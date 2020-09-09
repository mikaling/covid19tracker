package experiments.waweruu.c19tn.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import experiments.waweruu.c19tn.db.entity.CountrySlugEntity;

@Dao
public interface CountrySlugDao {

    @Insert
    void insert(List<CountrySlugEntity> entityList);

    @Query("DELETE FROM country_slug_table")
    void deleteAll();

    @Query("SELECT EXISTS(SELECT * FROM country_slug_table WHERE dateRetrieved = :dateRetrieved)")
    Boolean doesRowExist(int dateRetrieved);

    @Query("SELECT country FROM country_slug_table")
    LiveData<List<String>> getCountryNames();
}
