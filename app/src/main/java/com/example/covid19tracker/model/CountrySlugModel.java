package com.example.covid19tracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountrySlugModel {

    @SerializedName("Country")
    @Expose
    private String country;

    @SerializedName("CountryCode")
    @Expose
    private String countryCode;

    @SerializedName("Slug")
    @Expose
    private String slug;

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getSlug() {
        return slug;
    }
}
