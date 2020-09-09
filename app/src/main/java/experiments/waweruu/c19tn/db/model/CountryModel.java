package experiments.waweruu.c19tn.db.model;

import androidx.room.ColumnInfo;

public class CountryModel {

    @ColumnInfo(name = "country")
    private String countryName;
    @ColumnInfo(name = "totalConfirmed")
    private int confirmedCases;

    public CountryModel(String countryName, int confirmedCases) {
        this.countryName = countryName;
        this.confirmedCases = confirmedCases;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getConfirmedCases() {
        return confirmedCases;
    }
}
