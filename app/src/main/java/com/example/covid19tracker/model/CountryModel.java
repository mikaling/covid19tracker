package com.example.covid19tracker.model;

public class CountryModel {
    int confirmedCases;
    String countryName;

    public CountryModel(int confirmedCases, String countryName) {
        this.confirmedCases = confirmedCases;
        this.countryName = countryName;
    }


    public int getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(int confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
