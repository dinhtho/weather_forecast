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

fun createSampleWeatherInfo(): WeatherInfoDisplay {
    return WeatherInfoDisplay(
        time = "Thur,04,2021",
        averageTemperature = "20 C",
        pressure = "2000",
        humidity = "1000",
        description = "sky is blue"
    )
}

