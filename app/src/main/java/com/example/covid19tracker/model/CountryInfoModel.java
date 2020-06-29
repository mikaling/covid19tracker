package com.example.covid19tracker.model;

public class CountryInfoModel {

    private String name;
    private int number;

    public CountryInfoModel(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
