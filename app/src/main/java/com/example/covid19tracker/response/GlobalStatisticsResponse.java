package com.example.covid19tracker.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalStatisticsResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private GlobalStatisticsWrap globalStatisticsWrap;

    public String getStatus() {
        return status;
    }

    public GlobalStatisticsWrap getGlobalStatisticsWrap() {
        return globalStatisticsWrap;
    }
}
