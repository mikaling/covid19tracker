package com.example.covid19tracker.local;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.covid19tracker.R;
import com.example.covid19tracker.local.MyAxisValueFormatter;
import com.example.covid19tracker.local.MyValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class VisualizationActivity extends AppCompatActivity
{
    LineChart lineChart;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);

        lineChart();
        barChart();
    }

    private void lineChart()
    {
//       Initializing line chart from the view
        lineChart = findViewById(R.id.line_chart);

//      Creating the dataset fo each line in the graph together with its Label/Legend Value
        LineDataSet lineDataSetDeaths = new LineDataSet(deaths(),"Deaths");
        LineDataSet lineDataSetConfirmed = new LineDataSet(confirmed(),"Confirmed");
        LineDataSet lineDataSetRecoveries = new LineDataSet(recoveries(),"Recoveries");

//      Combines the datasets created above into one that will be used to feed the line chart with data
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSetDeaths);
        dataSets.add(lineDataSetConfirmed);
        dataSets.add(lineDataSetRecoveries);

//      Setting properties of the deaths line in the line chart
        lineDataSetDeaths.setLineWidth(4);
        lineDataSetDeaths.setColor(Color.parseColor("#180638"));
        lineDataSetDeaths.setDrawCircles(true);
        lineDataSetDeaths.setDrawCircleHole(true);
        lineDataSetDeaths.setCircleColor(Color.BLACK);
        lineDataSetDeaths.setCircleColorHole(Color.WHITE);
//      This value should be smaller than the value of circle radius
//        lineDataSet.setCircleHoleRadius(9);
        lineDataSetDeaths.setValueTextSize(10);
        lineDataSetDeaths.setValueTextColor(Color.BLACK);

//        Setting properties of the confirmed line in the line chart
        lineDataSetConfirmed.setLineWidth(4);
        lineDataSetConfirmed.setColor(Color.parseColor("#FF0000"));
        lineDataSetConfirmed.setDrawCircles(true);
        lineDataSetConfirmed.setDrawCircleHole(true);
        lineDataSetConfirmed.setCircleColor(Color.BLACK);
        lineDataSetConfirmed.setCircleColorHole(Color.WHITE);
//      This value should be smaller than the value of circle radius
//        lineDataSet.setCircleHoleRadius(9);
        lineDataSetConfirmed.setValueTextSize(10);
        lineDataSetConfirmed.setValueTextColor(Color.BLACK);

//        Setting properties of the recoveries line in the line chart
        lineDataSetRecoveries.setLineWidth(4);
        lineDataSetRecoveries.setColor(Color.parseColor("#f1f50a"));
        lineDataSetRecoveries.setDrawCircles(true);
        lineDataSetRecoveries.setDrawCircleHole(true);
        lineDataSetRecoveries.setCircleColor(Color.BLACK);
        lineDataSetRecoveries.setCircleColorHole(Color.WHITE);
//      This value should be smaller than the value of circle radius
//        lineDataSet.setCircleHoleRadius(9);
        lineDataSetDeaths.setValueTextSize(10);
        lineDataSetDeaths.setValueTextColor(Color.BLACK);

//      Get the legend object from the line chart
        Legend legend = lineChart.getLegend();

//      Set properties of the legend
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(15);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(20);
        legend.setXEntrySpace(15);
        legend.setFormToTextSpace(10);

//      Getting the axes objects from the line charts
        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxisLeft = lineChart.getAxisLeft();
        YAxis yAxisRight = lineChart.getAxisRight();

        xAxis.setValueFormatter(new MyAxisValueFormatter());
        yAxisLeft.setValueFormatter(new MyAxisValueFormatter());
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        yAxisLeft.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        yAxisRight.setEnabled(false);

//      Manually setting the description found at the bottom of the chart
        Description description = new Description();
        description.setText("Line Graph");
        description.setTextColor(Color.BLACK);
        description.setTextSize(10);
        lineChart.setDescription(description);

//      Wraps the chart in a rectangle
        lineChart.setDrawGridBackground(true);
        lineChart.setGridBackgroundColor(Color.parseColor("#4cada2"));
        lineChart.setBorderWidth(2);

//      Setting background colour for the chart
        lineChart.setBackgroundColor(Color.parseColor("#90b50b"));

//      Setting the message to be shown if no data is available in the chart
        lineChart.setNoDataText("No Data Available at the moment");
        lineChart.setNoDataTextColor(Color.RED);

//      Setting the data that the Line Chart will use through a LineData  object
        LineData data = new LineData(dataSets);
        data.setValueFormatter(new MyValueFormatter());
        lineChart.setData(data);
        lineChart.invalidate();
    }

    private void barChart()
    {
//      Initializing bar chart from the view
        barChart = findViewById(R.id.bar_chart);

        BarDataSet barDataSet = new BarDataSet(confirmedBarChart(),"Confirmed Cases");
        barDataSet.setColor(Color.parseColor("#FF0000"));

        XAxis xAxis = barChart.getXAxis();
        YAxis yAxisLeft = barChart.getAxisLeft();
        YAxis yAxisRight = barChart.getAxisRight();

        xAxis.setValueFormatter(new MyAxisValueFormatter());
        xAxis.setAxisMinimum(0);
        yAxisLeft.setValueFormatter(new MyAxisValueFormatter());
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        yAxisLeft.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        yAxisRight.setEnabled(false);

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

//    Method for setting the x and y values to be used for a line in the line chart
    private ArrayList<Entry> confirmed()
    {
        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(0,20));
        values.add(new Entry(1,24));
        values.add(new Entry(2,2));
        values.add(new Entry(3,10));
        values.add(new Entry(4,28));
        return values;
    }

    private ArrayList<Entry> deaths()
    {
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0,12));
        values.add(new Entry(2,16));
        values.add(new Entry(4,24));
        values.add(new Entry(6,28));
        values.add(new Entry(8,32));
        return values;
    }

    private ArrayList<Entry> recoveries()
    {
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0,10));
        values.add(new Entry(2,20));
        values.add(new Entry(4,30));
        values.add(new Entry(6,40));
        values.add(new Entry(8,50));
        return values;
    }

    //  Method for setting the x and y values to be used bar chart
    private ArrayList<BarEntry> confirmedBarChart()
    {
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0,3));
        values.add(new BarEntry(1,12));
        values.add(new BarEntry(2,21));
        values.add(new BarEntry(3,28));
        return values;
    }
}
