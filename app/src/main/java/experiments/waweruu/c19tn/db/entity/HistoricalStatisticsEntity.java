package experiments.waweruu.c19tn.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"dateRetrieved", "country", "reportDate"}, tableName = "historical_statistics_table")
public class HistoricalStatisticsEntity {

    private int dateRetrieved;
    @NonNull private String country;
    private int totalConfirmed;
    private int totalDeaths;
    private int totalRecovered;
    @NonNull private String reportDate;

    public HistoricalStatisticsEntity(int dateRetrieved, @NonNull String country,
                                      int totalConfirmed, int totalDeaths, int totalRecovered,
                                      @NotNull String reportDate) {
        this.dateRetrieved = dateRetrieved;
        this.country = country;
        this.totalConfirmed = totalConfirmed;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
        this.reportDate = reportDate;
    }

    public int getDateRetrieved() {
        return dateRetrieved;
    }

    @NonNull
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

    public String getReportDate() {
        return reportDate;
    }
}
