package com.volie.earthquake.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volie.earthquake.helper.DataLayerHelper
import com.volie.earthquake.model.EarthquakeModel
import kotlinx.coroutines.launch

class EarthquakeMapsViewModel(context: Context) : ViewModel() {

    val earthquakeMapsLiveData = MutableLiveData<EarthquakeModel>()

    private val dataLayerHelper = DataLayerHelper(context)


    fun getEarthquakesMaps(uuid: Int) {
        viewModelScope.launch {
            val earthquake = dataLayerHelper.repository.getEarthquakesMaps(uuid)
            earthquakeMapsLiveData.postValue(earthquake)
        }
    }


}