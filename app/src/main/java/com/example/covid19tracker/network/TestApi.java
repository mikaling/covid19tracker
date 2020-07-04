package com.example.covid19tracker.network;

import com.example.covid19tracker.response.BarDataResponse;
import com.example.covid19tracker.response.ContinentDataResponse;
import com.example.covid19tracker.response.CountryDataResponse;
import com.example.covid19tracker.response.GlobalStatisticsResponse;
import com.example.covid19tracker.response.HistoricalStatisticsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* This interface is where the endpoints of the API are defined */

public interface TestApi{

    /* Method structure
     * ---------------------------------------------------------------------------------------------
     * Annotation
     * =============================================================================================
     * Every method must have a HTTP annotation that provides the request method and
     * the relative URL
     * i.e. GET("users") means the request method is GET and the url to be appended
     * to the base url is "users"
     * The eight built in annotations in Retrofit are
     * HTTP, GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD
     * Method signature
     * =============================================================================================
     * All methods must have the return type as Call<T>
     * Depending on whether the endpoint requires input, a method may have parameters
     * This parameters however must also be annotated to indicate their type
     * Types include: Path, Body, Query, Header, Part, Field (list not exhaustive)*/

    /* This endpoint when given the name of a country as a slug / path returns a response
     * of a country's statistical data from the date of its first case
     * to the current date (the day the call has been made) */
    @GET("historical/{country}")
    Call<HistoricalStatisticsResponse> getCountryHistoricalData(@Path("country") String country);

    /* This endpoint returns a response detailing the global statistics of COVID-19 cases */
    @GET("globalStatistics")
    Call<GlobalStatisticsResponse> getGlobalStatistics();

    /* This endpoint returns an array of each country's most current statistics */
    @GET("countryData")
    Call<CountryDataResponse> getCountryData(@Query("limit") int limit);

    /* This endpoint returns an array of countries in East Africa and their total statistics */
    @GET("barData")
    Call<BarDataResponse> getBarData();

    /* This endpoint returns an array of statistics from countries in a
     * continent that is specified by providing the continent name as a slug
     */
    @GET("continents/{continent}")
    Call<ContinentDataResponse> getContinentData(@Path("continent") String continent);

}
