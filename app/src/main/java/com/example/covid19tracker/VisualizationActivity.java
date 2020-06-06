package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
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

import java.lang.reflect.Method;
import java.util.ArrayList;

public class VisualizationActivity extends AppCompatActivity
{
    LineChart lineChart;
    int[] lineColorArray = {R.color.colour1,R.color.colour2,R.color.colour3,R.color.colour4};
    int[] colorArray = new int[] {Color.BLUE,Color.CYAN,Color.YELLOW,Color.MAGENTA};
    String[] legendName = {"Cow","Dog","Cat","Rat"};

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);

//        LINE CHART
//       Initializing line chart from the view
        lineChart = findViewById(R.id.line_chart);

//      Creating the dataset fo each line in the graph together with its Label/Legend Value
        LineDataSet lineDataSet = new LineDataSet(dataValues(),"Data Set 1");
        LineDataSet lineDataSet1 = new LineDataSet(dataValues2(),"Data Set 2");
//      Combines the datasets created above into one that will be used to feed the line chart with data
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        dataSets.add(lineDataSet1);

//        Setting background colour for the chart
        lineChart.setBackgroundColor(Color.WHITE);

//      Setting the message to be shown if no data is available in the chart
        lineChart.setNoDataText("No Data");
        lineChart.setNoDataTextColor(Color.BLUE);
//      Wraps the chart in a rectangle
        lineChart.setDrawGridBackground(true);
        lineChart.setDrawBorders(true);
        lineChart.setBorderColor(Color.BLACK);
        lineChart.setBorderWidth(2);

//      Setting properties of the lines in the line chart
        lineDataSet.setLineWidth(4);
        lineDataSet.setColor(Color.MAGENTA);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setCircleColorHole(Color.WHITE);
        lineDataSet.setCircleRadius(10);
//      This value should be smaller than the value of circle radius
        lineDataSet.setCircleHoleRadius(9);
        lineDataSet.setValueTextSize(10);
        lineDataSet.setValueTextColor(Color.BLUE);
//        lineDataSet.enableDashedLine(5,10,0);
        lineDataSet.setColors(lineColorArray,this);

//      Get the legend object from the line chart
        Legend legend = lineChart.getLegend();

//      Set properties of the legend
        legend.setEnabled(true);
        legend.setTextColor(Color.RED);
        legend.setTextSize(15);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(20);
        legend.setXEntrySpace(15);
        legend.setFormToTextSpace(10);

        LegendEntry[] legendEntries = new LegendEntry[4];
        for (int i=0; i<legendEntries.length; i++)
        {
            LegendEntry legendEntry = new LegendEntry();
//          Color of the legend is not dependent on the color of the line in the chart
            legendEntry.formColor = colorArray[i];
            legendEntry.label = String.valueOf(legendName[i]);
            legendEntries[i] = legendEntry;
        }
        legend.setCustom(legendEntries);

//      Getting the axes objects from the line charts
        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxisLeft = lineChart.getAxisLeft();
        YAxis yAxisRight = lineChart.getAxisRight();

        xAxis.setValueFormatter(new MyAxisValueFormatter());
        yAxisLeft.setValueFormatter(new MyAxisValueFormatter());

//      Manually setting the description found at the bottom of the chart
        Description description = new Description();
        description.setText("Line Graph");
        description.setTextColor(Color.BLACK);
        description.setTextSize(20);
        lineChart.setDescription(description);

//      Setting the data that the Line Chart will use through a LineData  object
        LineData data = new LineData(dataSets);
        data.setValueFormatter(new MyValueFormatter());
        lineChart.setData(data);
        lineChart.invalidate();

//                                          BAR CHART
//      Initializing bar chart from the view
        barChart = findViewById(R.id.bar_chart);

        BarDataSet barDataSet = new BarDataSet(dataValues3(),"Data Set 1");
        barDataSet.setColor(Color.BLUE);

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        barChart.setData(barData);
        barChart.invalidate();
    }

//    Method for setting the x and y values to be used for a line in the line chart
    private ArrayList<Entry> dataValues()
    {
        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(0,20));
        values.add(new Entry(1,24));
        values.add(new Entry(2,2));
        values.add(new Entry(3,10));
        values.add(new Entry(4,28));
        return values;
    }

    private ArrayList<Entry> dataValues2()
    {
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0,12));
        values.add(new Entry(2,16));
        values.add(new Entry(4,24));
        values.add(new Entry(6,28));
        values.add(new Entry(8,32));
        return values;
    }

    //  Method for setting the x and y values to be used bar chart
    private ArrayList<BarEntry> dataValues3()
    {
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0,3));
        values.add(new BarEntry(1,12));
        values.add(new BarEntry(2,21));
        values.add(new BarEntry(3,28));
        return values;
    }
}
