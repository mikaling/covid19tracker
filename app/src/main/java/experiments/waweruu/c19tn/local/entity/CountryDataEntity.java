package experiments.waweruu.c19tn.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"country", "dateRetrieved"}, tableName = "country_data_table")
public class CountryDataEntity {

    @NonNull private String country;
    private int totalConfirmed;
    private int totalDeaths;
    private int totalRecovered;
    private float recoveryRate;
    private float deathRate;
    private int dateRetrieved;

    public CountryDataEntity(@NotNull String country, int totalConfirmed, int totalDeaths,
                             int totalRecovered, float recoveryRate, float deathRate,
                             int dateRetrieved) {
        this.country = country;
        this.totalConfirmed = totalConfirmed;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
        this.recoveryRate = recoveryRate;
        this.deathRate = deathRate;
        this.dateRetrieved = dateRetrieved;
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

    public float getRecoveryRate() {
        return recoveryRate;
    }

    public float getDeathRate() {
        return deathRate;
    }

    public int getDateRetrieved() {
        return dateRetrieved;
    }
}
