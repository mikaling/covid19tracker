package com.example.covid19tracker.local;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.covid19tracker.R;
import com.example.covid19tracker.model.BarDataModel;
import com.example.covid19tracker.network.RetrofitClientInstance;
import com.example.covid19tracker.network.TestApi;
import com.example.covid19tracker.response.BarDataResponse;
import com.example.covid19tracker.utils.VerticalTextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.covid19tracker.utils.Utils;

import static com.example.covid19tracker.utils.Utils.showToast;

public class BarChartFragment extends Fragment
{
    BarChart barChart;
    List<BarDataModel> barDataModels = new ArrayList<>();
    TextView xAxisLabel;

    public BarChartFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_bar_chart, container, false);
        barChart = view.findViewById(R.id.bar_chart_fragment);
        TestApi service = RetrofitClientInstance.getRetrofitInstance().create(TestApi.class);
        Call<BarDataResponse> barDataResponseCall = service.getBarData();
        barDataResponseCall.enqueue(new Callback<BarDataResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(Call<BarDataResponse> call, Response<BarDataResponse> response) {
                if(response.body().getStatus().equals(Utils.RESPONSE_SUCCESS_ALT)) {
                    barDataModels = response.body().getData().getBarDataModelList();
                    Log.i("BarChartFragment", barDataModels.toString());
                    barChart();
                }
            }

            @Override
            public void onFailure(Call<BarDataResponse> call, Throwable t) {
                showToast(getActivity(), getString(R.string.retrofit_on_failure_message));
                Log.e("Bar Data Error", t.getMessage(), t);
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void barChart()
    {
        float barSpace = 0f;
        float groupSpace = 0.1f;

//      Initializing bar chart from the view
        BarDataSet barDataSetConfirmed = new BarDataSet(confirmedBarChart(),"Confirmed Cases");
        barDataSetConfirmed.setColor(Color.parseColor("#FF0000"));
        BarDataSet barDataSetDeaths = new BarDataSet(deathsBarChart(),"Deaths");
        barDataSetDeaths.setColor(Color.parseColor("#330b5c"));
        BarDataSet barDataSetRecoveries = new BarDataSet(recoveriesBarChart(),"Recoveries");
        barDataSetRecoveries.setColor(Color.parseColor("#f1f50a"));

        XAxis xAxis = barChart.getXAxis();
        YAxis yAxisLeft = barChart.getAxisLeft();
        YAxis yAxisRight = barChart.getAxisRight();

        String[] barChartLabels = new String[]{"Burundi","Kenya","Rwanda","Tanzania","Uganda"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(barChartLabels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        yAxisLeft.setDrawLabels(true);
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxisRight.setEnabled(false);

//        //Changed parameter from getActivity() to getContext()
//        xAxisLabel = new TextView(this.getContext());
//        xAxisLabel.setText(R.string.bar_chart_xaxis_label);
//        xAxisLabel.setTextColor(Color.BLACK);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
//        params.setMargins(0, 0, 0, 20);
//
//        //Changed parameter from getActivity() to getContext()
//        VerticalTextView yAxisLabel = new VerticalTextView(this.getContext(),null);
//        yAxisLabel.setText(R.string.bar_chart_y_axis_label);
//        yAxisLabel.setTextColor(Color.BLACK);
//        yAxisLabel.setRotation(180);
//        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        params2.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
//        params2.setMargins(0,0,40,40);
//        getActivity().addContentView(xAxisLabel,params);
//        getActivity().addContentView(yAxisLabel,params2);

        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        barChart.setDrawGridBackground(true);
        barChart.setGridBackgroundColor(Color.parseColor("#4cada2"));
        barChart.setBackgroundColor(Color.parseColor("#90b50b"));

        BarData barData = new BarData(barDataSetConfirmed,barDataSetDeaths,barDataSetRecoveries);
        barData.setBarWidth(0.3f);

        barChart.setData(barData);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(5);
        barChart.groupBars(0,groupSpace,barSpace);

        barChart.invalidate();
    }

    //  Method for setting the x and y values to be used bar chart
    private ArrayList<BarEntry> confirmedBarChart()
    {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < barDataModels.size(); i++)
        {
            BarDataModel barDataModel = barDataModels.get(i);
            values.add(new BarEntry(i, barDataModel.getBarDataTotalConfirmedCases()));
        }
        return values;
    }

    private ArrayList<BarEntry> deathsBarChart()
    {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < barDataModels.size(); i++)
        {
            BarDataModel barDataModel = barDataModels.get(i);
            values.add(new BarEntry(i, barDataModel.getBarDataTotalDeaths()));
        }
        return values;
    }

    private ArrayList<BarEntry> recoveriesBarChart()
    {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < barDataModels.size(); i++)
        {
            BarDataModel barDataModel = barDataModels.get(i);
            values.add(new BarEntry(i, barDataModel.getBarDataTotalRecoveries()));
        }
        return values;
    }
}
