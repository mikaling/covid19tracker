package experiments.waweruu.c19tn.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import experiments.waweruu.c19tn.db.entity.HistoricalStatisticsEntity;
import experiments.waweruu.c19tn.db.model.CountryInfoModel;

@Dao
public interface HistoricalStatisticsDao {

    @Insert
    void insert(List<HistoricalStatisticsEntity> entityList);

    @Query("DELETE FROM historical_statistics_table WHERE country = :country")
    void deleteCountry(String country);

    @Query("SELECT country, totalConfirmed, totalDeaths, totalRecovered FROM historical_statistics_table WHERE dateRetrieved = :dateRetrieved" +
            " AND country = :country")
    LiveData<List<CountryInfoModel>> getHistoricalData(int dateRetrieved, String country);

    @Query("SELECT EXISTS(SELECT * FROM historical_statistics_table WHERE dateRetrieved =" +
            " :dateRetrieved AND country = :country)")
    Boolean doesRowExist(int dateRetrieved, String country);
}
