package com.example.covid19tracker.network;

import com.example.covid19tracker.response.CountryDataResponse;
import com.example.covid19tracker.response.GlobalStatisticsResponse;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Covid19ApiAlt {

    @GET("country-data/{alias}")
    Observable<CountryDataResponse> getTopCountryData(@Path("alias") String alias);

    @GET("summary")
    Observable<GlobalStatisticsResponse> getGlobalStatistics();

    @GET("country-data")
    Observable<CountryDataResponse> getCountryData(@Query("limit") int limit,
                                                   @Query("page") int page);


}
