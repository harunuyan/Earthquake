package com.volie.earthquake.data

import android.util.Log
import com.volie.earthquake.model.EarthquakeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EarthquakeRepository(
    private val earthquakeService: EarthquakeService,
    private val earthquakeDao: EarthquakeDao
) {
    suspend fun getEarthquakesFromRemote(): List<EarthquakeModel> {
        return withContext(Dispatchers.IO) {
            val result = kotlin.runCatching {
                earthquakeService.getEarthquakesFromAPI()
            }.onFailure {
                Log.e("Remote", "$it")
                return@withContext getEarthquakesFromLocal()
            }.getOrNull()
            if (result?.isSuccessful == true) {
                val list = result.body()?.data.orEmpty()
                earthquakeDao.insertData(list)
                list
            } else {
                emptyList()
            }
        }
    }

    suspend fun getEarthquakesFromLocal(): List<EarthquakeModel> {
        return withContext(Dispatchers.IO) {
            earthquakeDao.getAllData()
        }
    }

    fun searchDatabase(searchQuery: String): List<EarthquakeModel> {
        return earthquakeDao.searchDatabase(searchQuery)
    }
}