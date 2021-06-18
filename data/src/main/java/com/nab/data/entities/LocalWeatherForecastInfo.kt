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

fun createSampleLocalInfo():LocalWeatherForecastInfo{
    return LocalWeatherForecastInfo(
        cityName = "Sai gon",
        dt = 100000,
        temp = "{}",
        pressure = 1000,
        humidity = 1000,
        weather = "[]"
    )
}

