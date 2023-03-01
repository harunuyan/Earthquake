package com.volie.earthquake.helper

import android.content.Context
import com.volie.earthquake.data.EarthquakeDatabase
import com.volie.earthquake.data.EarthquakeRepository
import com.volie.earthquake.data.EarthquakeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataLayerHelper(context: Context) {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.orhanaydogdu.com.tr/deprem/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val database = EarthquakeDatabase.getInstance(context)
    val earthquakeDao = database.earthquakeDao()

    private val earthquakeService = retrofit.create(EarthquakeService::class.java)
    val repository = EarthquakeRepository(earthquakeService, earthquakeDao)
}