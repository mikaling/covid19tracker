package experiments.waweruu.c19tn.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "global_statistics_table")
public class GlobalStatisticsEntity {

    @PrimaryKey
    private int dateRetrieved;

    private int newConfirmed;

    private int newDeaths;

    private int newRecovered;

    private int totalConfirmed;

    private int totalDeaths;

    private int totalRecovered;

    public GlobalStatisticsEntity(int dateRetrieved, int newConfirmed, int newDeaths, int newRecovered, int totalConfirmed, int totalDeaths, int totalRecovered) {
        this.newConfirmed = newConfirmed;
        this.newDeaths = newDeaths;
        this.newRecovered = newRecovered;
        this.totalConfirmed = totalConfirmed;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
        this.dateRetrieved = dateRetrieved;
    }

    public int getDateRetrieved() {
        return dateRetrieved;
    }

    public int getNewConfirmed() {
        return newConfirmed;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public int getNewRecovered() {
        return newRecovered;
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

    public void setDateRetrieved(int dateRetrieved) {
        this.dateRetrieved = dateRetrieved;
    }
}
