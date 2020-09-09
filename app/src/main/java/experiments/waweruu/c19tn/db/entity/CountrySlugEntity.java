package experiments.waweruu.c19tn.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"dateRetrieved", "country"}, tableName = "country_slug_table")
public class CountrySlugEntity {

    private int dateRetrieved;
    @NonNull private String country;
    private String countryCode;
    private String slug;

    public CountrySlugEntity(int dateRetrieved, @NonNull String country, String countryCode, String slug) {
        this.dateRetrieved = dateRetrieved;
        this.country = country;
        this.countryCode = countryCode;
        this.slug = slug;
    }

    public int getDateRetrieved() {
        return dateRetrieved;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getSlug() {
        return slug;
    }
}
