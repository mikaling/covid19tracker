package experiments.waweruu.c19tn.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import experiments.waweruu.c19tn.db.dao.CountrySlugDao;
import experiments.waweruu.c19tn.db.entity.CountrySlugEntity;

@Database(entities = {CountrySlugEntity.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract CountrySlugDao countrySlugDao();
}
