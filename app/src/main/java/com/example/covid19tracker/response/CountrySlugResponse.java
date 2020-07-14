package com.example.covid19tracker.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountrySlugResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("results")
    @Expose
    private int results;

    @SerializedName("data")
    @Expose
    private CountrySlugWrap wrap;

    public String getStatus() {
        return status;
    }

    public int getResults() {
        return results;
    }

    public CountrySlugWrap getWrap() {
        return wrap;
    }
}
