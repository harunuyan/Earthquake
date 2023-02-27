package com.volie.earthquake.model

data class EarthquakeModel(
    val magnitude: Double,
    val name: String,
    val date: String,
    val time: String
) {
    val magnitudeColor: Int
        get() = when (magnitude) {
            in 0.0..2.0 -> 0xFF00FF00.toInt()
            in 2.1..3.9 -> 0xFFFFFF00.toInt()
            in 4.0..4.9 -> 0xFFFFA500.toInt()
            in 5.0..5.9 -> 0xFFFF0000.toInt()
            in 6.0..6.9 -> 0xFF800080.toInt()
            in 7.0..7.9 -> 0xFF800000.toInt()
            else -> 0xFF000000.toInt()
        }
    val magnitudeColorLight: Int
        get() = when (magnitude) {
            in 0.0..2.0 -> 0x3300FF00.toInt()
            in 2.1..3.9 -> 0x33FFFF00.toInt()
            in 4.0..4.9 -> 0x33FFA500.toInt()
            in 5.0..5.9 -> 0x33FF0000.toInt()
            in 6.0..6.9 -> 0x33800080.toInt()
            in 7.0..7.9 -> 0x33800000.toInt()
            else -> 0x33000000.toInt()
        }
    val magnitudeText: String
        get() = String.format("%.1f", magnitude)
}