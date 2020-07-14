package com.example.covid19tracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarDataModel
{
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
    private String id;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("totalConfirmed")
    @Expose
    private int totalConfirmed;

    @SerializedName("totalDeaths")
    @Expose
    private int totalDeaths;

    @SerializedName("totalRecovered")
    @Expose
    private int totalRecovered;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("_v")
    @Expose
    private int version;

    public BarDataModel(String id, String country, int totalConfirmed,
                        int totalDeaths, int totalRecovered, String date, int version) {
        this.id = id;
        this.country = country;
        this.totalConfirmed = totalConfirmed;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
        this.date = date;
        this.version = version;
    }

    public String getCountry() {
        return country;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public String getDate() {
        return date;
    }
}
