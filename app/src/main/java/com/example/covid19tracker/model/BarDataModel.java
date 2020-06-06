package com.example.covid19tracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarDataModel {

    /* SerializedName is an annotation that gives the name of the variable in the response from the
     * API. Expose atm I have no idea what it does, but the jsonschema2pojo website includes it,
     * so ¯\_(ツ)_/¯ */

    /* Sample representation of this class in JSON
     * {
     * "_id":"5edb878ab1faa523009d0cfa",
     * "country":"Burundi",
     * "totalConfirmed":63,
     * "totalDeaths":1,
     * "totalRecovered":33,
     * "date":"2020-06-06T12:08:43Z",
     * "__v":0
     * } */

    @SerializedName("_id")
    @Expose
    private String barDataId;

    @SerializedName("country")
    @Expose
    private String barDataCountry;

    @SerializedName("totalConfirmed")
    @Expose
    private int barDataTotalConfirmedCases;

    @SerializedName("totalDeaths")
    @Expose
    private int barDataTotalDeaths;

    @SerializedName("totalRecovered")
    @Expose
    private int barDataTotalRecoveries;

    @SerializedName("date")
    @Expose
    private String barDataReportDate;

    @SerializedName("_v")
    @Expose
    private int barDataVersion;

    public BarDataModel(String barDataId, String barDataCountry, int barDataTotalConfirmedCases,
                        int barDataTotalDeaths, int barDataTotalRecoveries, String barDataReportDate, int barDataVersion) {
        this.barDataId = barDataId;
        this.barDataCountry = barDataCountry;
        this.barDataTotalConfirmedCases = barDataTotalConfirmedCases;
        this.barDataTotalDeaths = barDataTotalDeaths;
        this.barDataTotalRecoveries = barDataTotalRecoveries;
        this.barDataReportDate = barDataReportDate;
        this.barDataVersion = barDataVersion;
    }

    public String getBarDataCountry() {
        return barDataCountry;
    }

    public int getBarDataTotalConfirmedCases() {
        return barDataTotalConfirmedCases;
    }

    public int getBarDataTotalDeaths() {
        return barDataTotalDeaths;
    }

    public int getBarDataTotalRecoveries() {
        return barDataTotalRecoveries;
    }

    public String getBarDataReportDate() {
        return barDataReportDate;
    }
}
