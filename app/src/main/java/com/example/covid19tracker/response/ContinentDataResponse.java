package com.example.covid19tracker.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContinentDataResponse {
    
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("results")
    @Expose
    private int results;

    @SerializedName("data")
    @Expose
    private ContinentDataWrap continentDataWrap;

    public String getStatus() {
        return status;
    }

    public int getResults() {
        return results;
    }

    public ContinentDataWrap getContinentDataWrap() {
        return continentDataWrap;
    }
}
