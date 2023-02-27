package com.volie.earthquake.model

import android.graphics.Color

data class EarthquakeModel(
    val magnitude: Double,
    val name: String,
    val date: String,
    val time: String
) {
    val magnitudeColor: Int
        get() = when (magnitude) {
            in 0.0..2.9 -> Color.parseColor("#3ff10c")
            in 3.0..6.9 -> Color.parseColor("#efc30e")
            in 7.0..9.9 -> Color.parseColor("#f10c0c")
            else -> Color.WHITE
        }
    val magnitudeColorLight: Int
        get() = when (magnitude) {
            in 0.0..2.9 -> Color.parseColor("#defede")
            in 3.0..6.9 -> Color.parseColor("#fdf7dc")
            in 7.0..9.9 -> Color.parseColor("#fde9e9")
            else -> Color.WHITE
        }
    val magnitudeText: String
        get() = String.format("%.1f", magnitude)
}