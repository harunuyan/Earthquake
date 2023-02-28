package com.volie.earthquake.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.volie.earthquake.model.EarthquakeModel

@Dao
interface EarthquakeDao {

    @Query("SELECT * FROM earthquakes")
    fun getAllData(): List<EarthquakeModel>

    @Insert
    fun insert(earthquakeList: List<EarthquakeModel>)

    @Query("SELECT * FROM earthquakes WHERE name LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<EarthquakeModel>>
}