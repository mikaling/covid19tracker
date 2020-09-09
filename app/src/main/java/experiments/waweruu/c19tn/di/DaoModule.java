package experiments.waweruu.c19tn.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import experiments.waweruu.c19tn.db.dao.ContinentCountryDataDao;
import experiments.waweruu.c19tn.db.dao.ContinentTotalsDao;
import experiments.waweruu.c19tn.db.dao.CountryDataDao;
import experiments.waweruu.c19tn.db.dao.CountrySlugDao;
import experiments.waweruu.c19tn.db.dao.GlobalStatisticsDao;
import experiments.waweruu.c19tn.db.database.ContinentalDatabase;
import experiments.waweruu.c19tn.db.database.GlobalDatabase;
import experiments.waweruu.c19tn.db.database.LocalDatabase;

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

    @Provides
    @Singleton
    CountrySlugDao provideCountrySlugDao(LocalDatabase localDatabase) {
        return localDatabase.countrySlugDao();
    }
}
