package experiments.waweruu.c19tn.remote.wrap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import experiments.waweruu.c19tn.remote.model.HistoricalStatisticsModel;

public class HistoricalStatisticsWrap {

    @SerializedName("countries")
    @Expose
    private List<HistoricalStatisticsModel> historicalStatisticsModelList;

    public List<HistoricalStatisticsModel> getHistoricalStatisticsModelList() {
        return historicalStatisticsModelList;
    }
}
