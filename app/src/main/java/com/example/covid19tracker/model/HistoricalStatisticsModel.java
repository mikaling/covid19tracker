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
    private String country;

    @SerializedName("Confirmed")
    @Expose
    private int confirmed;

    @SerializedName("Deaths")
    @Expose
    private int deaths;

    @SerializedName("Recovered")
    @Expose
    private int recovered;

    @SerializedName("Active")
    @Expose
    private int active;

    @SerializedName("Date")
    @Expose
    private String date;

    public HistoricalStatisticsModel(String country, int confirmed, int deaths, int recovered, int active, String date) {
        this.country = country;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getActive() {
        return active;
    }

    public String getDate() {
        return date;
    }
}
