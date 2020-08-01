package com.example.covid19tracker.response;

import com.example.covid19tracker.model.ContinentTotalsModel;
import com.example.covid19tracker.model.CountryDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContinentDataWrap {

    @SerializedName("countries")
    @Expose
    private List<CountryDataModel> countryDataModelList;

    @SerializedName("totals")
    @Expose
    private ContinentTotalsModel continentTotalsModel;

    public List<CountryDataModel> getCountryDataModelList() {
        return countryDataModelList;
    }

    public ContinentTotalsModel getContinentTotalsModel() {
        return continentTotalsModel;
    }
}
