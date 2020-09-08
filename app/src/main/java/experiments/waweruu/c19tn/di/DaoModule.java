package experiments.waweruu.c19tn.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import experiments.waweruu.c19tn.local.dao.ContinentCountryDataDao;
import experiments.waweruu.c19tn.local.dao.ContinentTotalsDao;
import experiments.waweruu.c19tn.local.dao.CountryDataDao;
import experiments.waweruu.c19tn.local.dao.GlobalStatisticsDao;
import experiments.waweruu.c19tn.local.database.ContinentalDatabase;
import experiments.waweruu.c19tn.local.database.GlobalDatabase;

@Module
public class DaoModule {

    @Provides
    @Singleton
    GlobalStatisticsDao provideGlobalStatisticsDao(GlobalDatabase globalDatabase) {
        return globalDatabase.globalStatisticsDao();
    }

    @Provides
    @Singleton
    CountryDataDao provideCountryDataDao(GlobalDatabase globalDatabase) {
        return globalDatabase.countryDataDao();
    }

    @Provides
    @Singleton
    ContinentTotalsDao provideContinentTotalsDao(ContinentalDatabase continentalDatabase) {
        return continentalDatabase.continentTotalsDao();
    }

    @Provides
    @Singleton
    ContinentCountryDataDao provideContinentCountryDataDao(ContinentalDatabase continentalDatabase) {
        return continentalDatabase.continentCountryDataDao();
    }
}
