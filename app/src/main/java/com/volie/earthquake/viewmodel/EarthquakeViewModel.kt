package com.volie.earthquake.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volie.earthquake.model.EarthquakeModel
import kotlinx.coroutines.launch

class EarthquakeViewModel : ViewModel() {

    private val _earthquakes = MutableLiveData<List<EarthquakeModel>>()
    val earthquakes: LiveData<List<EarthquakeModel>> = _earthquakes

    fun getEarthquakesFromRemote() {
        viewModelScope.launch {

        }
    }
}