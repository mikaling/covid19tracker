package experiments.waweruu.c19tn.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import experiments.waweruu.c19tn.db.entity.ContinentCountryDataEntity;
import experiments.waweruu.c19tn.db.model.CountryModel;


@Dao
public interface ContinentCountryDataDao {

    @Insert
    void insert(List<ContinentCountryDataEntity> entityList);

    @Query("DELETE FROM continent_country_data_table WHERE continent = :continent")
    void deleteContinent(String continent);

    @Query("SELECT * FROM continent_country_data_table WHERE dateRetrieved = :dateRetrieved" +
            " AND continent = :continent")
    LiveData<List<ContinentCountryDataEntity>> getContinentCountryData(int dateRetrieved,
                                                                       String continent);

    @Query("SELECT country, totalConfirmed FROM continent_country_data_table" +
            " WHERE dateRetrieved = :dateRetrieved AND continent = :continent")
    LiveData<List<CountryModel>> getCountryModelList(int dateRetrieved, String continent);

    @Query("SELECT EXISTS(SELECT * FROM continent_country_data_table WHERE dateRetrieved = :dateRetrieved" +
            " AND continent =:continent LIMIT 'ONE')")
    Boolean doesRowExist(int dateRetrieved, String continent);
}
