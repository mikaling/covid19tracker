package experiments.waweruu.c19tn.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import experiments.waweruu.c19tn.db.entity.GlobalStatisticsEntity;

@Dao
public interface GlobalStatisticsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GlobalStatisticsEntity entity);

    @Query("DELETE FROM global_statistics_table")
    void deleteAll();

    @Query("SELECT * FROM global_statistics_table WHERE dateRetrieved = :dateRetrieved")
    LiveData<GlobalStatisticsEntity> getGlobalStatistics(int dateRetrieved);

    @Query("SELECT EXISTS(SELECT * FROM global_statistics_table WHERE dateRetrieved = :dateRetrieved)")
    Boolean doesRowExist(int dateRetrieved);
}
