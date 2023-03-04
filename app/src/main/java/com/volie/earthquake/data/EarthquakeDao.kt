package com.volie.earthquake.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.volie.earthquake.model.EarthquakeModel

@Dao
interface EarthquakeDao {

    @Query("SELECT * FROM earthquakes")
    fun getAllData(): List<EarthquakeModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(earthquakeList: List<EarthquakeModel>)

    @Query("SELECT * FROM earthquakes WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchDatabase(searchQuery: String): List<EarthquakeModel>

    @Query("SELECT * FROM earthquakes ORDER BY magnitude DESC")
    suspend fun sortHighMag(): List<EarthquakeModel>

    @Query("SELECT * FROM earthquakes ORDER BY magnitude ASC")
    suspend fun sortLowMag(): List<EarthquakeModel>
}