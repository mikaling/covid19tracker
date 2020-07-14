package com.example.covid19tracker.response;

import com.example.covid19tracker.model.CountrySlugModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountrySlugWrap {

    @SerializedName("countries")
    @Expose
    private List<CountrySlugModel> modelList;

    public List<CountrySlugModel> getModelList() {
        return modelList;
    }
}
