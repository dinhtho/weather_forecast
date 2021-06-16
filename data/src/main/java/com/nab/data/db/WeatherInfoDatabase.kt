package com.nab.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nab.data.entities.LocalWeatherForecastInfo

@Database(entities = [LocalWeatherForecastInfo::class], version = 1,exportSchema = false)
abstract class WeatherInfoDatabase : RoomDatabase() {
    abstract fun weatherInfoDao(): WeatherInfoDAO

    companion object {
        private const val DATABASE_NAME = "WeatherForecastInfo"
        fun buildDatabase(context: Context): WeatherInfoDatabase {
            return Room.databaseBuilder(context, WeatherInfoDatabase::class.java, DATABASE_NAME).build()
        }
    }
}