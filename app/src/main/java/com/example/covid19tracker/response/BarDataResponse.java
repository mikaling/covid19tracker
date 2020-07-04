package com.example.covid19tracker.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarDataResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("results")
    @Expose
    private int results;

    @SerializedName("data")
    @Expose
    private BarDataWrap data;

    public String getStatus() {
        return status;
    }

    public int getResults() {
        return results;
    }

    public BarDataWrap getData() {
        return data;
    }
}
