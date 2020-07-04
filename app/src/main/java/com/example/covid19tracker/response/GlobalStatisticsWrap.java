package com.example.covid19tracker.response;

import com.example.covid19tracker.model.GlobalStatisticsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalStatisticsWrap {
    @SerializedName("stats")
    @Expose
    private GlobalStatisticsModel globalStatisticsModel;

    public GlobalStatisticsModel getGlobalStatisticsModel() {
        return globalStatisticsModel;
    }
}
