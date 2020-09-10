package experiments.waweruu.c19tn.remote.retrofit;

import experiments.waweruu.c19tn.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofitInstance;

    public static Retrofit getRetrofitInstance() {
        if(retrofitInstance == null) {
            retrofitInstance = new retrofit2.Retrofit.Builder()
                    .baseUrl(String.valueOf(R.string.API_BASE_URL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

}
