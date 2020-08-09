package com.example.covid19tracker.local;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.covid19tracker.R;
import com.example.covid19tracker.model.HistoricalStatisticsModel;
import com.example.covid19tracker.network.RetrofitClientInstance;
import com.example.covid19tracker.network.TestApi;
import com.example.covid19tracker.response.HistoricalStatisticsResponse;
import com.example.covid19tracker.utils.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.covid19tracker.utils.Utils.showToast;

public class LineChartFragment extends Fragment {
    private LineChart lineChart;
    private TextView title;
    private List<HistoricalStatisticsModel> historicalStatisticsModels = new ArrayList<>();
    private String slug, country;
    RelativeLayout layoutLineChart;

    public LineChartFragment() {
        // Required empty public constructor
    }

    public static LineChartFragment newInstance(String slug, String country) {
        LineChartFragment fragment = new LineChartFragment();
        Bundle args = new Bundle();
        args.putString("slug", slug);
        args.putString("country", country);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        slug = getArguments().getString("slug");
        country = getArguments().getString("country");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);
        lineChart = view.findViewById(R.id.line_chart_fragment);
        title = view.findViewById(R.id.label_line_chart_title);
        layoutLineChart = view.findViewById(R.id.fragment_line_chart);
        fetchLineData();

        return view;
    }

    private void lineChart() {
//      Creating the dataset fo each line in the graph together with its Label/Legend Value
        LineDataSet lineDataSetConfirmed = new LineDataSet(confirmed(), "Confirmed");
        LineDataSet lineDataSetDeaths = new LineDataSet(deaths(), "Deaths");
        LineDataSet lineDataSetRecoveries = new LineDataSet(recoveries(), "Recoveries");

//      Combines the datasets created above into one that will be used to feed the line chart with data
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSetDeaths);
        dataSets.add(lineDataSetConfirmed);
        dataSets.add(lineDataSetRecoveries);

//      Setting properties of the deaths line in the line chart
        lineDataSetDeaths.setLineWidth(1.5f);
        lineDataSetDeaths.setColor(Color.parseColor("#180638"));
        lineDataSetDeaths.setDrawCircles(false);
        lineDataSetDeaths.setDrawCircleHole(false);
//        lineDataSetDeaths.setCircleColor(Color.BLACK);
//        lineDataSetDeaths.setCircleColorHole(Color.WHITE);
////      This value should be smaller than the value of circle radius
////        lineDataSet.setCircleHoleRadius(9);
        lineDataSetDeaths.setValueTextSize(10);
        lineDataSetDeaths.setValueTextColor(Color.BLACK);

//        Setting properties of the confirmed line in the line chart
        lineDataSetConfirmed.setLineWidth(1.5f);
        lineDataSetConfirmed.setColor(Color.parseColor("#FF0000"));
        lineDataSetConfirmed.setDrawCircles(false);
        lineDataSetConfirmed.setDrawCircleHole(false);
//        lineDataSetConfirmed.setCircleColor(Color.BLACK);
//        lineDataSetConfirmed.setCircleColorHole(Color.WHITE);
//      This value should be smaller than the value of circle radius
//        lineDataSet.setCircleHoleRadius(9);
        lineDataSetConfirmed.setValueTextSize(10);
        lineDataSetConfirmed.setValueTextColor(Color.BLACK);

//        Setting properties of the recoveries line in the line chart
        lineDataSetRecoveries.setLineWidth(1.5f);
        lineDataSetRecoveries.setColor(Color.parseColor("#f1f50a"));
        lineDataSetRecoveries.setDrawCircles(false);
        lineDataSetRecoveries.setDrawCircleHole(false);
//        lineDataSetRecoveries.setCircleColor(Color.BLACK);
//        lineDataSetRecoveries.setCircleColorHole(Color.WHITE);
////      This value should be smaller than the value of circle radius
////        lineDataSet.setCircleHoleRadius(9);
        lineDataSetDeaths.setValueTextSize(10);
        lineDataSetDeaths.setValueTextColor(Color.BLACK);

//      Get the legend object from the line chart
        Legend legend = lineChart.getLegend();

//      Set properties of the legend
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(10);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(20);
        legend.setXEntrySpace(10);
        legend.setFormToTextSpace(10);

//      Getting the axes objects from the line charts
        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxisLeft = lineChart.getAxisLeft();
        YAxis yAxisRight = lineChart.getAxisRight();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setAxisLineColor(Color.BLACK);
        yAxisRight.setEnabled(false);

//      Manually setting the description found at the bottom of the chart
        Description description = new Description();
        description.setText("");
        description.setTextColor(Color.BLACK);
        description.setTextSize(10);
        lineChart.setDescription(description);

//      Wraps the chart in a rectangle
//        lineChart.setDrawGridBackground(true);
//        lineChart.setGridBackgroundColor(Color.parseColor("#4cada2"));
//        lineChart.setBorderWidth(2);

//      Setting background colour for the chart
        lineChart.setBackgroundColor(Color.parseColor("#4cada2"));

//      Setting the message to be shown if no data is available in the chart
        lineChart.setNoDataText("No Data Available at the moment");
        lineChart.setNoDataTextColor(Color.RED);

//      Setting the data that the Line Chart will use through a LineData  object
        LineData data = new LineData(dataSets);
        data.setValueFormatter(new MyValueFormatter());
        lineChart.setData(data);
        lineChart.invalidate();
//        title.setText("Trends of COVID-19 in " + country);
        title.setText(
                String.format(Locale.ENGLISH, "Trends of COVID-19 in %s", country)
        );
    }

    //  Method for setting the x and y values to be used for a line in the line chart
    private ArrayList<Entry> confirmed() {
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < historicalStatisticsModels.size(); i++) {
            HistoricalStatisticsModel historicalStatisticsModel = historicalStatisticsModels.get(i);
            values.add(new Entry(i + 1, historicalStatisticsModel.getConfirmed()));
        }
        return values;
    }

    private ArrayList<Entry> deaths() {
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < historicalStatisticsModels.size(); i++) {
            HistoricalStatisticsModel historicalStatisticsModel = historicalStatisticsModels.get(i);
            values.add(new Entry(i + 1, historicalStatisticsModel.getDeaths()));
        }
        return values;
    }

    private ArrayList<Entry> recoveries() {
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < historicalStatisticsModels.size(); i++) {
            HistoricalStatisticsModel historicalStatisticsModel = historicalStatisticsModels.get(i);
            values.add(new Entry(i + 1, historicalStatisticsModel.getRecovered()));
        }
        return values;
    }

    private void fetchLineData()
    {
        TestApi service = RetrofitClientInstance.getRetrofitInstance().create(TestApi.class);
        Call<HistoricalStatisticsResponse> historicalStatisticsResponseCall = service
                .getCountryHistoricalData(slug);
        historicalStatisticsResponseCall.enqueue(new Callback<HistoricalStatisticsResponse>() {
            @Override
            public void onResponse(Call<HistoricalStatisticsResponse> call, Response<HistoricalStatisticsResponse> response) {
                assert response.body() != null;
                if (response.body().getStatus().equals(Utils.RESPONSE_SUCCESS)) {
                    historicalStatisticsModels = response.body().getHistoricalStatisticsWrap()
                            .getHistoricalStatisticsModelList();
                    lineChart();
                }
            }

            @Override
            public void onFailure(Call<HistoricalStatisticsResponse> call, Throwable t) {
//                showToast(getActivity(), getString(R.string.retrofit_on_failure_message));
                Log.e("Bar Data Error", t.getMessage(), t);
                showErrorSnackBar();
            }
        });
    }

    private void showErrorSnackBar()
    {
        final Snackbar snackbar = Snackbar.make(layoutLineChart, "Error Loading Data", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", v -> {
            fetchLineData();
            snackbar.dismiss();
        });
        snackbar.show();
    }
}
