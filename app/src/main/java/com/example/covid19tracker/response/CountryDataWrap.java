package com.example.covid19tracker.response;

import com.example.covid19tracker.model.CountryDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CountryDataWrap {
    @SerializedName("countries")
    @Expose
    private ArrayList<CountryDataModel> countryDataModelList;

    public ArrayList<CountryDataModel> getCountryDataModelList() {
        return countryDataModelList;
    }
}
