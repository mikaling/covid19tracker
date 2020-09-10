package experiments.waweruu.c19tn.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import experiments.waweruu.c19tn.remote.wrap.GlobalStatisticsWrap;

public class GlobalStatisticsResponse {

    /* {
        "status": "success",
        "data": {
            "stats": {
                "NewConfirmed": 226979,
                "TotalConfirmed": 23644647,
                "NewDeaths": 4346,
                "TotalDeaths": 812981,
                "NewRecovered": 200042,
                "TotalRecovered": 15335952
            }
        }
    } */

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private GlobalStatisticsWrap globalStatisticsWrap;

    public GlobalStatisticsResponse(String status, GlobalStatisticsWrap globalStatisticsWrap) {
        this.status = status;
        this.globalStatisticsWrap = globalStatisticsWrap;
    }

    public String getStatus() {
        return status;
    }

    public GlobalStatisticsWrap getGlobalStatisticsWrap() {
        return globalStatisticsWrap;
    }
}
