package com.volie.earthquake.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.volie.earthquake.model.EarthquakeModel

@Database(entities = [EarthquakeModel::class], version = 1)
abstract class EarthquakeDatabase : RoomDatabase() {

    abstract fun earthquakeDao(): EarthquakeDao

    companion object {

        @Volatile
        private var INSTANCE: EarthquakeDatabase? = null
        fun getInstance(context: Context): EarthquakeDatabase {
            synchronized(this) {
                if (INSTANCE != null) {
                    return INSTANCE!!
                }
                val instance = Room.databaseBuilder(
                    context,
                    EarthquakeDatabase::class.java, "database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
            }
            return INSTANCE!!
        }
    }
}