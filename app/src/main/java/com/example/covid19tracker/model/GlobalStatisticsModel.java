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
    private int newConfirmed;

    @SerializedName("TotalConfirmed")
    @Expose
    private int totalConfirmed;

    @SerializedName("NewDeaths")
    @Expose
    private int newDeaths;

    @SerializedName("TotalDeaths")
    @Expose
    private int totalDeaths;

    @SerializedName("NewRecovered")
    @Expose
    private int newRecovered;

    @SerializedName("TotalRecovered")
    @Expose
    private int totalRecovered;

    public GlobalStatisticsModel(int newConfirmed, int totalConfirmed, int newDeaths,
                                 int totalDeaths, int newRecovered, int totalRecovered) {
        this.newConfirmed = newConfirmed;
        this.totalConfirmed = totalConfirmed;
        this.newDeaths = newDeaths;
        this.totalDeaths = totalDeaths;
        this.newRecovered = newRecovered;
        this.totalRecovered = totalRecovered;
    }

    public int getNewConfirmed() {
        return newConfirmed;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getNewRecovered() {
        return newRecovered;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }
}
