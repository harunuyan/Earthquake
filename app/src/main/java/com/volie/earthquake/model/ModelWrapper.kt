package com.volie.earthquake.model

import com.google.gson.annotations.SerializedName

data class EarthquakeWrapper(
    @SerializedName("result")
    val data: List<EarthquakeModel>
)