package com.volie.earthquake.data

import com.volie.earthquake.model.EarthquakeWrapper
import retrofit2.Response
import retrofit2.http.GET

interface EarthquakeService {

    @GET("live.php?limit=200")
    suspend fun getEarthquakesFromAPI(): Response<EarthquakeWrapper>
}