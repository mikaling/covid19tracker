package experiments.waweruu.c19tn.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import experiments.waweruu.c19tn.local.entity.CountryDataEntity;
import experiments.waweruu.c19tn.local.model.CountryInfoModel;

@Dao
public interface CountryDataDao {

    @Insert
    void insert(List<CountryDataEntity> entityList);

    @Query("DELETE FROM country_data_table")
    void deleteAll();

    @Query("SELECT * FROM country_data_table WHERE dateRetrieved = :dateRetrieved")
    LiveData<List<CountryDataEntity>> getCountryData(int dateRetrieved);

    @Query("SELECT country, totalConfirmed, totalDeaths, totalRecovered FROM country_data_table" +
        " WHERE dateRetrieved = :dateRetrieved")
    LiveData<List<CountryInfoModel>> getCountryInfo(int dateRetrieved);

    @Query("SELECT EXISTS(SELECT * FROM country_data_table WHERE dateRetrieved = :dateRetrieved LIMIT 'ONE')")
    Boolean doesRowExist(int dateRetrieved);


}
