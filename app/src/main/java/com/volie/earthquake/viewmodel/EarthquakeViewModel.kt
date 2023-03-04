package com.volie.earthquake.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volie.earthquake.helper.DataLayerHelper
import com.volie.earthquake.model.EarthquakeModel
import kotlinx.coroutines.launch

class EarthquakeViewModel(context: Context) : ViewModel() {

    private val _earthquakes = MutableLiveData<List<EarthquakeModel>>()
    val earthquakes: LiveData<List<EarthquakeModel>> = _earthquakes

    private val dataLayerHelper = DataLayerHelper(context)

    private suspend fun getEarthquakesFromRemote(): List<EarthquakeModel> {
        return dataLayerHelper.repository.getEarthquakesFromRemote()
    }

    private suspend fun getEarthquakesFromLocal(): List<EarthquakeModel> {
        return dataLayerHelper.repository.getEarthquakesFromLocal()
    }

    fun getEarthquakes() {
        viewModelScope.launch {
            val localData = getEarthquakesFromLocal()
            if (localData.isEmpty()) {
                val remoteData = getEarthquakesFromRemote()
                _earthquakes.postValue(remoteData)
                return@launch
            }
            _earthquakes.postValue(localData)
        }
    }

    fun refreshEarthquakes() {
        viewModelScope.launch {
            val result = getEarthquakesFromRemote()
            _earthquakes.postValue(result)
        }
    }

    fun searchDatabase(searchQuery: String) {
        viewModelScope.launch {
            val search = dataLayerHelper.repository.searchDatabase(searchQuery)
            _earthquakes.postValue(search)
        }
    }
}