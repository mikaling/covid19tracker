package experiments.waweruu.c19tn.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import experiments.waweruu.c19tn.db.dao.CountrySlugDao;
import experiments.waweruu.c19tn.db.dao.HistoricalStatisticsDao;
import experiments.waweruu.c19tn.db.entity.CountrySlugEntity;
import experiments.waweruu.c19tn.db.entity.HistoricalStatisticsEntity;

@Database(entities = {CountrySlugEntity.class, HistoricalStatisticsEntity.class}, version = 3, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract CountrySlugDao countrySlugDao();

    public abstract HistoricalStatisticsDao historicalStatisticsDao();
}
