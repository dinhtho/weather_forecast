package com.nab.data.entities

import androidx.room.Entity

@Entity(tableName = "WeatherInfo", primaryKeys = ["cityName","dt"])
data class LocalWeatherForecastInfo(
    val cityName: String,
    val dt: Long,
    val temp: String,
    val pressure: Int,
    val humidity: Int,
    val weather: String
)

