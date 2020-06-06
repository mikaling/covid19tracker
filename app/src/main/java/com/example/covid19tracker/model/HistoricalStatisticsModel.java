package com.example.covid19tracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoricalStatisticsModel {

    /* SerializedName is an annotation that gives the name of the variable in the response from the
     * API. Expose atm I have no idea what it does, but the jsonschema2pojo website includes it,
     * so ¯\_(ツ)_/¯*/

    /* Sample representation of this class in JSON
     * {
     * "Country": "Kenya",
     * "Confirmed": 1,
     * "Deaths": 0,
     * "Recovered": 0,
     * "Active": 1,
     * "Date": "2020-03-13T00:00:00Z"
     * }*/

    @SerializedName("Country")
    @Expose
    private String countryName;

    @SerializedName("Confirmed")
    @Expose
    private int confirmedCases;

    @SerializedName("Deaths")
    @Expose
    private int confirmedDeaths;

    @SerializedName("Recovered")
    @Expose
    private int confirmedRecoveries;

    @SerializedName("Active")
    @Expose
    private int confirmedActiveCases;

    @SerializedName("Date")
    @Expose
    private String dateOfStatisticReport;

    public HistoricalStatisticsModel(String countryName, int confirmedCases, int confirmedDeaths, int confirmedRecoveries, int confirmedActiveCases, String dateOfStatisticReport) {
        this.countryName = countryName;
        this.confirmedCases = confirmedCases;
        this.confirmedDeaths = confirmedDeaths;
        this.confirmedRecoveries = confirmedRecoveries;
        this.confirmedActiveCases = confirmedActiveCases;
        this.dateOfStatisticReport = dateOfStatisticReport;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getConfirmedCases() {
        return confirmedCases;
    }

    public int getConfirmedDeaths() {
        return confirmedDeaths;
    }

    public int getConfirmedRecoveries() {
        return confirmedRecoveries;
    }

    public int getConfirmedActiveCases() {
        return confirmedActiveCases;
    }

    public String getDateOfStatisticReport() {
        return dateOfStatisticReport;
    }
}
