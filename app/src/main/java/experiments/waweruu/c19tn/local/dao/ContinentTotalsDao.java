package experiments.waweruu.c19tn.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import experiments.waweruu.c19tn.local.entity.ContinentTotalsEntity;

@Dao
public interface ContinentTotalsDao {

    @Insert
    void insert(ContinentTotalsEntity entity);

    @Query("DELETE FROM continent_totals_table WHERE continent = :continent")
    void deleteContinent(String continent);

    @Query("SELECT * FROM continent_totals_table WHERE continent = :continent")
    LiveData<ContinentTotalsEntity> getContinentTotals(String continent);

    @Query("SELECT EXISTS(SELECT * FROM continent_totals_table WHERE dateRetrieved = :dateRetrieved" +
            " AND continent = :continent)")
    Boolean doesRowExist(int dateRetrieved, String continent);
}
