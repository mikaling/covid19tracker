package com.example.covid19tracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryDataModel {

    /* SerializedName is an annotation that gives the name of the variable in the response from the
     * API. Expose atm I have no idea what it does, but the jsonschema2pojo website includes it,
     * so ¯\_(ツ)_/¯ */

    /* Sample representation of this class in JSON
     * {
     * "_id":"5edb878ab1faa523009d0cdf",
     * "country":"Afghanistan",
     * "totalConfirmed":18969,
     * "totalDeaths":309,
     * "totalRecovered":1762,
     * "date":"2020-06-06T12:08:43Z",
     * "__v":0
     * } */

    @SerializedName("_id")
    @Expose
    private String countryDataId;

    @SerializedName("country")
    @Expose
    private String countryDataName;

    @SerializedName("totalConfirmed")
    @Expose
    private String countryDataTotalConfirmedCases;

    @SerializedName("totalDeaths")
    @Expose
    private String countryDataTotalDeaths;

    @SerializedName("totalRecovered")
    @Expose
    private String countryDataTotalRecoveries;

    @SerializedName("date")
    @Expose
    private String countryDataReportDate;

    @SerializedName("_v")
    @Expose
    private int countryDataVersion;

    public CountryDataModel(String countryDataId, String countryDataName,
                            String countryDataTotalConfirmedCases, String countryDataTotalDeaths,
                            String countryDataTotalRecoveries, String countryDataReportDate,
                            int countryDataVersion) {
        this.countryDataId = countryDataId;
        this.countryDataName = countryDataName;
        this.countryDataTotalConfirmedCases = countryDataTotalConfirmedCases;
        this.countryDataTotalDeaths = countryDataTotalDeaths;
        this.countryDataTotalRecoveries = countryDataTotalRecoveries;
        this.countryDataReportDate = countryDataReportDate;
        this.countryDataVersion = countryDataVersion;
    }

    public String getCountryDataName() {
        return countryDataName;
    }

    public String getCountryDataTotalConfirmedCases() {
        return countryDataTotalConfirmedCases;
    }

    public String getCountryDataTotalDeaths() {
        return countryDataTotalDeaths;
    }

    public String getCountryDataTotalRecoveries() {
        return countryDataTotalRecoveries;
    }

    public String getCountryDataReportDate() {
        return countryDataReportDate;
    }
}
