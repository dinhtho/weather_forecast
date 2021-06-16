package com.nab.domain.entities

/**
 * Created by tho nguyen on 6/15/2021.
 * dinhthohcmus@gmail.com
 */
data class WeatherInfoDisplay(
    val time: String,
    val averageTemperature: String,
    val pressure: String,
    val humidity: String,
    val description: String
) {
}