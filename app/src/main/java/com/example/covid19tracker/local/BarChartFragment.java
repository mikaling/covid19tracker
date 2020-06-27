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
        Call<List<BarDataModel>> barChartData = service.getBarData();
        barChartData.enqueue(new Callback<List<BarDataModel>>()
        {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(Call<List<BarDataModel>> call, Response<List<BarDataModel>> response)
            {
                barDataModels = response.body();
                barChart();
            }

            @Override
            public void onFailure(Call<List<BarDataModel>> call, Throwable t)
            {
                showToast(getActivity(), getString(R.string.retrofit_on_failure_message));
                Log.e("Bar Data Error", t.getMessage(), t);
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void barChart()
    {
//      Initializing bar chart from the view
        BarDataSet barDataSet = new BarDataSet(confirmedBarChart(),"Confirmed Cases");
        barDataSet.setColor(Color.parseColor("#FF0000"));

        XAxis xAxis = barChart.getXAxis();
        YAxis yAxisLeft = barChart.getAxisLeft();
        YAxis yAxisRight = barChart.getAxisRight();

        String[] barChartLabels = new String[]{"Burundi","Kenya","Rwanda","Tanzania","Uganda"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(barChartLabels));
//        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisMinimum(0);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        yAxisLeft.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        yAxisRight.setEnabled(false);

        xAxisLabel = new TextView(getActivity());
        xAxisLabel.setText(R.string.bar_chart_xaxis_label);
        xAxisLabel.setTextColor(Color.BLACK);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        params.setMargins(0, 0, 0, 20);

        VerticalTextView yAxisLabel = new VerticalTextView(getActivity(),null);
        yAxisLabel.setText(R.string.bar_chart_yaxis_label);
        yAxisLabel.setTextColor(Color.BLACK);
        yAxisLabel.setRotation(180);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        params2.setMargins(0,0,40,40);
        getActivity().addContentView(xAxisLabel,params);
        getActivity().addContentView(yAxisLabel,params2);

        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        barChart.setDrawGridBackground(true);
        barChart.setGridBackgroundColor(Color.parseColor("#4cada2"));
        barChart.setBackgroundColor(Color.parseColor("#90b50b"));

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        barChart.setData(barData);
        barChart.invalidate();
    }

    //  Method for setting the x and y values to be used bar chart
    private ArrayList<BarEntry> confirmedBarChart()
    {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i=0; i < barDataModels.size(); i++)
        {
            BarDataModel barDataModel = barDataModels.get(i);
            values.add(new BarEntry(i,barDataModel.getBarDataTotalConfirmedCases()));
        }
        return values;
    }
}
