package com.example.covid19tracker.response;

import com.example.covid19tracker.model.BarDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarDataWrap {
    @SerializedName("countries")
    @Expose
    private List<BarDataModel> barDataModelList;

    public List<BarDataModel> getBarDataModelList() {
        return barDataModelList;
    }
}
