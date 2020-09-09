package experiments.waweruu.c19tn.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import experiments.waweruu.c19tn.db.dao.CountryDataDao;
import experiments.waweruu.c19tn.db.dao.GlobalStatisticsDao;
import experiments.waweruu.c19tn.db.entity.CountryDataEntity;
import experiments.waweruu.c19tn.db.entity.GlobalStatisticsEntity;

@Database(entities = {GlobalStatisticsEntity.class, CountryDataEntity.class}, version = 2, exportSchema = false)
public abstract class GlobalDatabase extends RoomDatabase {

//    private static GlobalDatabase instance;

    public abstract GlobalStatisticsDao globalStatisticsDao();

    public abstract CountryDataDao countryDataDao();

//    private static final int NUMBER_OF_THREADS = 4;
//    static final ExecutorService databaseWriteExecutor = Executors
//            .newFixedThreadPool(NUMBER_OF_THREADS);

//    public static synchronized GlobalDatabase getInstance(Context context) {
//        if(instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(), GlobalDatabase.class,
//                    "global_database")
//                    .fallbackToDestructiveMigration()
//                    .build();
//        }
//        return instance;
//    }


}
