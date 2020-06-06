package com.example.covid19tracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalStatisticsModel {

    /* SerializedName is an annotation that gives the name of the variable in the response from the
     * API. Expose atm I have no idea what it does, but the jsonschema2pojo website includes it,
     * so ¯\_(ツ)_/¯ */

    /* Sample representation of this class in JSON
     * {
     * "NewConfirmed": 101781,
     * "TotalConfirmed": 6829314,
     * "NewDeaths": 3800,
     * "TotalDeaths": 402636,
     * "NewRecovered": 131192,
     * "TotalRecovered": 3000504
     * } */

    @SerializedName("NewConfirmed")
    @Expose
    private int newConfirmedCases;

    @SerializedName("TotalConfirmed")
    @Expose
    private int totalConfirmedCases;

    @SerializedName("NewDeaths")
    @Expose
    private int newDeaths;

    @SerializedName("TotalDeaths")
    @Expose
    private int totalDeaths;

    @SerializedName("NewRecovered")
    @Expose
    private int newRecoveries;

    @SerializedName("TotalRecovered")
    @Expose
    private int totalRecoveries;

    public GlobalStatisticsModel(int newConfirmedCases, int totalConfirmedCases, int newDeaths,
                                 int totalDeaths, int newRecoveries, int totalRecoveries) {
        this.newConfirmedCases = newConfirmedCases;
        this.totalConfirmedCases = totalConfirmedCases;
        this.newDeaths = newDeaths;
        this.totalDeaths = totalDeaths;
        this.newRecoveries = newRecoveries;
        this.totalRecoveries = totalRecoveries;
    }

    public int getNewConfirmedCases() {
        return newConfirmedCases;
    }

    public int getTotalConfirmedCases() {
        return totalConfirmedCases;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getNewRecoveries() {
        return newRecoveries;
    }

    public int getTotalRecoveries() {
        return totalRecoveries;
    }
}
