package experiments.waweruu.c19tn.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"dateRetrieved", "continent"}, tableName = "continent_totals_table")
public class ContinentTotalsEntity {

    private int dateRetrieved;
    @NonNull private String continent;
    private int totalConfirmed;
    private int totalRecoveries;
    private int totalDeaths;

    public ContinentTotalsEntity(int dateRetrieved, @NonNull String continent, int totalConfirmed, int totalRecoveries, int totalDeaths) {
        this.dateRetrieved = dateRetrieved;
        this.continent = continent;
        this.totalConfirmed = totalConfirmed;
        this.totalRecoveries = totalRecoveries;
        this.totalDeaths = totalDeaths;
    }

    public int getDateRetrieved() {
        return dateRetrieved;
    }

    @NonNull
    public String getContinent() {
        return continent;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public int getTotalRecoveries() {
        return totalRecoveries;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }
}
