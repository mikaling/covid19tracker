package experiments.waweruu.c19tn.remote.wrap;

import com.google.gson.annotations.SerializedName;

import experiments.waweruu.c19tn.remote.model.GlobalStatisticsModel;

public class GlobalStatisticsWrap {

    @SerializedName("stats")
    private GlobalStatisticsModel globalStatisticsModel;

    public GlobalStatisticsModel getGlobalStatisticsModel() {
        return globalStatisticsModel;
    }
}
