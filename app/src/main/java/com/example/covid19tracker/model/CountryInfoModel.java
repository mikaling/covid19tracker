package com.example.covid19tracker.model;

public class CountryInfoModel {

    private String country;
    private int confirmed;
    private int deaths;
    private int recoveries;

    public CountryInfoModel(String country, int confirmed, int deaths, int recoveries) {
        this.country = country;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recoveries = recoveries;
    }
}
