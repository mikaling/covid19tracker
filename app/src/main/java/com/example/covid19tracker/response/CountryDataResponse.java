package com.example.covid19tracker.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryDataResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("page")
    @Expose
    private int pageNumber;

    @SerializedName("data")
    @Expose
    private CountryDataWrap countryDataWrap;

    public String getStatus() {
        return status;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public CountryDataWrap getCountryDataWrap() {
        return countryDataWrap;
    }
}
