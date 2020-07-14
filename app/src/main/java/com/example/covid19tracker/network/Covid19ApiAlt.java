package com.example.covid19tracker.network;

import com.example.covid19tracker.response.CountryDataResponse;
import com.example.covid19tracker.response.GlobalStatisticsResponse;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Covid19ApiAlt {

    @GET("countryData/{alias}")
    Observable<CountryDataResponse> getTopCountryData(@Path("alias") String alias);

    @GET("globalStatistics")
    Observable<GlobalStatisticsResponse> getGlobalStatistics();

    @GET("countryData")
    Observable<CountryDataResponse> getCountryData(@Query("limit") int limit,
                                                   @Query("page") int page);


}
