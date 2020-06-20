package com.example.covid19tracker.local;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class MyAxisValueFormatter implements IAxisValueFormatter
{
    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {
        axis.setLabelCount(3,true);
        return ""+value;
    }
}
