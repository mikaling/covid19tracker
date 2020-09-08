package experiments.waweruu.c19tn.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import experiments.waweruu.c19tn.R;
import experiments.waweruu.c19tn.remote.retrofit.ApiService;
import experiments.waweruu.c19tn.util.Util;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RemoteModule {

    @Provides
    Retrofit provideRetrofit(RxJava3CallAdapterFactory adapterFactory) {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(Util.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(adapterFactory)
                .build();
    }

    @Provides
    RxJava3CallAdapterFactory provideCallAdapterFactory() {
        return RxJava3CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
