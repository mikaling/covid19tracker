package experiments.waweruu.c19tn.db.model;


public class CountryInfoModel {

    private String country;
    private int totalConfirmed;
    private int totalDeaths;
    private int totalRecovered;

    public CountryInfoModel(String country, int totalConfirmed, int totalDeaths, int totalRecovered) {
        this.country = country;
        this.totalConfirmed = totalConfirmed;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
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
}
