package experiments.waweruu.c19tn.local.model;


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
}
