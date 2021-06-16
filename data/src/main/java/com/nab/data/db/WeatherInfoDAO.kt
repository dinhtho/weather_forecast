package com.nab.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nab.data.entities.LocalWeatherForecastInfo

@Dao
interface WeatherInfoDAO {

    @Query("SELECT * FROM WeatherInfo WHERE cityName LIKE :cityName")
    suspend fun getLocalWeatherInfos(cityName : String) : List<LocalWeatherForecastInfo>

    @Query("delete FROM WeatherInfo")
    suspend fun removeLocalData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherInfos(info: List<LocalWeatherForecastInfo>)
}