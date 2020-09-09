package experiments.waweruu.c19tn.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import experiments.waweruu.c19tn.AppExecutors;
import experiments.waweruu.c19tn.db.dao.ContinentCountryDataDao;
import experiments.waweruu.c19tn.db.dao.ContinentTotalsDao;
import experiments.waweruu.c19tn.db.dao.CountryDataDao;
import experiments.waweruu.c19tn.db.dao.GlobalStatisticsDao;
import experiments.waweruu.c19tn.db.database.ContinentalDatabase;
import experiments.waweruu.c19tn.db.database.GlobalDatabase;
import experiments.waweruu.c19tn.db.database.LocalDatabase;
import experiments.waweruu.c19tn.remote.retrofit.ApiService;
import experiments.waweruu.c19tn.repository.ContinentalRepository;
import experiments.waweruu.c19tn.repository.CountryDataRepository;
import experiments.waweruu.c19tn.repository.GlobalStatisticsRepository;

@Module
public class LocalModule {

    @Provides
    @Singleton
    GlobalDatabase provideGlobalDatabase(Application application) {
        return Room.databaseBuilder(application, GlobalDatabase.class,
                "global_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    ContinentalDatabase provideContinentalDatabase(Application application) {
        return Room.databaseBuilder(application, ContinentalDatabase.class,
                "continental_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    LocalDatabase provideLocalDatabase(Application application) {
        return Room.databaseBuilder(application, LocalDatabase.class, "local_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Provides
    @Singleton
    GlobalStatisticsRepository provideGlobalRepository(AppExecutors appExecutors, ApiService apiService,
                                                       GlobalStatisticsDao globalStatisticsDao) {
        return new GlobalStatisticsRepository(appExecutors, apiService, globalStatisticsDao);
    }

    @Provides
    @Singleton
    CountryDataRepository provideCountryDataRepository(AppExecutors appExecutors,
                                                       ApiService apiService,
                                                       CountryDataDao countryDataDao) {
        return new CountryDataRepository(appExecutors, apiService, countryDataDao);
    }

    @Provides
    @Singleton
    ContinentalRepository provideContinentalRepository(AppExecutors appExecutors,
                                                       ApiService apiService,
                                                       ContinentCountryDataDao continentCountryDataDao,
                                                       ContinentTotalsDao continentTotalsDao) {
        return new ContinentalRepository(appExecutors, apiService, continentCountryDataDao,
                continentTotalsDao);
    }
}
