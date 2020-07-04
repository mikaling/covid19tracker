package com.example.covid19tracker.response;

import com.example.covid19tracker.model.CountryDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryDataWrap {
    @SerializedName("countries")
    @Expose
    private List<CountryDataModel> countryDataModelList;

    public List<CountryDataModel> getCountryDataModelList() {
        return countryDataModelList;
    }
}
