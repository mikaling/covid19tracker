package com.example.covid19tracker.model;

import androidx.annotation.Nullable;

public class CountryInfoModel {

    private String country;
    private int confirmed;
    @Nullable
    private int deaths;
    @Nullable
    private int recoveries;

    public CountryInfoModel(String country, int confirmed, @Nullable int deaths, @Nullable int recoveries) {
        this.country = country;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recoveries = recoveries;
    }
}
