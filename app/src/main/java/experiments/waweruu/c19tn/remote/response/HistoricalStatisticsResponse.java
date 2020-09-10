package experiments.waweruu.c19tn.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import experiments.waweruu.c19tn.remote.wrap.HistoricalStatisticsWrap;

public class HistoricalStatisticsResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("results")
    @Expose
    private int results;

    @SerializedName("data")
    @Expose
    private HistoricalStatisticsWrap historicalStatisticsWrap;

    public String getStatus() {
        return status;
    }

    public int getResults() {
        return results;
    }

    public HistoricalStatisticsWrap getHistoricalStatisticsWrap() {
        return historicalStatisticsWrap;
    }
}
