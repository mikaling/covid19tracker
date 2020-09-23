package experiments.waweruu.c19tn.db.entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"dateRetrieved", "country", "continent"}, tableName = "continent_country_data_table")
public class ContinentCountryDataEntity {

    private int dateRetrieved;
    @NonNull private String country;
    @NonNull private String continent;
    private int totalConfirmed;
    private int totalDeaths;
    private int totalRecovered;
    private float recoveryRate;
    private float deathRate;

    public ContinentCountryDataEntity(@NonNull String country, @NonNull String continent,
                                      int dateRetrieved, int totalConfirmed, int totalDeaths,
                                      int totalRecovered, float recoveryRate, float deathRate) {
        this.country = country;
        this.continent = continent;
        this.dateRetrieved = dateRetrieved;
        this.totalConfirmed = totalConfirmed;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
        this.recoveryRate = recoveryRate;
        this.deathRate = deathRate;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    @NonNull
    public String getContinent() {
        return continent;
    }

    public int getDateRetrieved() {
        return dateRetrieved;
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
}
