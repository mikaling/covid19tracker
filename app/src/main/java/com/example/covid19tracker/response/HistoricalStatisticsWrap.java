package com.example.covid19tracker.response;

import com.example.covid19tracker.model.HistoricalStatisticsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoricalStatisticsWrap {

    @SerializedName("countries")
    @Expose
    private List<HistoricalStatisticsModel> historicalStatisticsModelList;

    public List<HistoricalStatisticsModel> getHistoricalStatisticsModelList() {
        return historicalStatisticsModelList;
    }
}
