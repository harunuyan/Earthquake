package com.volie.earthquake.data

import com.volie.earthquake.model.EarthquakeModel

class EarthquakeRepository(
    private val earthquakeService: EarthquakeService
) {
    suspend fun getEarthquakesFromRemote(): List<EarthquakeModel> {

        val result = earthquakeService.getEarthquakesFromAPI()
        if (result.isSuccessful) {
            return result.body()?.data.orEmpty()
        } else {
            return emptyList()
        }
    }
}