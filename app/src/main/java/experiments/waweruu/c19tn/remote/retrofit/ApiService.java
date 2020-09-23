package experiments.waweruu.c19tn.remote.retrofit;

import experiments.waweruu.c19tn.remote.response.ContinentDataResponse;
import experiments.waweruu.c19tn.remote.response.CountryDataResponse;
import experiments.waweruu.c19tn.remote.response.CountrySlugResponse;
import experiments.waweruu.c19tn.remote.response.GlobalStatisticsResponse;
import experiments.waweruu.c19tn.remote.response.HistoricalStatisticsResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

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


    /* This endpoint returns a response detailing the global statistics of COVID-19 cases */
    @GET("summary")
    Call<GlobalStatisticsResponse> getGlobalStatistics();

    /* This endpoint returns an array of each country's most current statistics */
    @GET("country-data")
    Observable<CountryDataResponse> getCountryData(@Query("limit") int limit,
                                                   @Query("page") int page);

    /* This endpoint returns an array of statistics from countries in a
     * continent that is specified by providing the continent name as a slug
     */
    @GET("continents/{continent}")
    Observable<ContinentDataResponse> getContinentData(@Path("continent") String continent,
                                                 @Query("limit") int limit,
                                                 @Query("page") int page);

    /* This endpoint returns the list of countries that we have data on */
    @GET("countries")
    Call<CountrySlugResponse> getCountrySlugs();

    /* This endpoint when given the name of a country as a slug / path returns a response
     * of a country's statistical data from the date of its first case
     * to the current date (the day the call has been made) */
    @GET("historical/{country}")
    Call<HistoricalStatisticsResponse> getCountryHistoricalData(@Path("country") String country);


}
