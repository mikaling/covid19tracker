package experiments.waweruu.c19tn.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import experiments.waweruu.c19tn.db.dao.ContinentCountryDataDao;
import experiments.waweruu.c19tn.db.dao.ContinentTotalsDao;
import experiments.waweruu.c19tn.db.entity.ContinentCountryDataEntity;
import experiments.waweruu.c19tn.db.entity.ContinentTotalsEntity;

@Database(entities = {ContinentCountryDataEntity.class, ContinentTotalsEntity.class}, version = 1,
        exportSchema = false)
public abstract class ContinentalDatabase extends RoomDatabase {

    public abstract ContinentCountryDataDao continentCountryDataDao();

    public abstract ContinentTotalsDao continentTotalsDao();
}
