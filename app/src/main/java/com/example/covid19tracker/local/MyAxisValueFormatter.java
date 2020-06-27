package com.example.covid19tracker.local;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class MyAxisValueFormatter implements IAxisValueFormatter
{
    private final String[] barChartLabels;

    public MyAxisValueFormatter(String[] barChartLabels)
    {

        this.barChartLabels = barChartLabels;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {
//        axis.setLabelCount(3,true);
        return barChartLabels[(int) value];
    }
}
