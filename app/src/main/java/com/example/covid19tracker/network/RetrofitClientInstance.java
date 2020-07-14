package com.example.covid19tracker.network;

import com.example.covid19tracker.utils.Utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* Here we define a single instance of Retrofit using the Retrofit.Builder class that we will use
 * within the project. We also configure it with a base URL and a converter factory. By default,
 * Retrofit can only deserialize HTTP bodies into OKHttp's ResponseBody type and it can only accept
 * its RequestBody type for @Body. We add the GSON converter that supports JSON. */

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static Retrofit retrofitAlternative;
    private static final RxJava3CallAdapterFactory adapter = RxJava3CallAdapterFactory.create();

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Utils.API_BASE_URL_V2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getAlternativeRetrofitInstance() {
        if(retrofitAlternative == null) {
            retrofitAlternative = new retrofit2.Retrofit.Builder()
                    .baseUrl(Utils.API_BASE_URL_V2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(adapter)
                    .build();
        }
        return retrofitAlternative;
    }
}
