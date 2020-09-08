package experiments.waweruu.c19tn.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import experiments.waweruu.c19tn.local.dao.ContinentCountryDataDao;
import experiments.waweruu.c19tn.local.dao.ContinentTotalsDao;
import experiments.waweruu.c19tn.local.entity.ContinentCountryDataEntity;
import experiments.waweruu.c19tn.local.entity.ContinentTotalsEntity;

@Database(entities = {ContinentCountryDataEntity.class, ContinentTotalsEntity.class}, version = 1,
        exportSchema = false)
public abstract class ContinentalDatabase extends RoomDatabase {

    public abstract ContinentCountryDataDao continentCountryDataDao();

    public abstract ContinentTotalsDao continentTotalsDao();
}
