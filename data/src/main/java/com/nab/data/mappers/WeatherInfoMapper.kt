package com.nab.data.mappers

import com.nab.data.entities.TemperatureResponse
import com.nab.data.entities.WeatherInfoResponse
import com.nab.domain.entities.WeatherInfo
import java.text.SimpleDateFormat
import java.util.*

const val DESCRIPTION_SEPARATOR = ", "
const val CELSIUS_SYMBOL = "\u2103"
const val PERCENT_SYMBOL = "%"
internal const val DATE_FORMAT = "EEE, dd MMM yyy"

fun WeatherInfoResponse.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        time = (dt * 1000).toTimeFormat(),
        averageTemperature = temp.getAverageTempDisplay(),
        humidity = "$humidity$PERCENT_SYMBOL",
        pressure = "$pressure",
        description = weather.joinToString(separator = DESCRIPTION_SEPARATOR) { weatherResponse ->
            weatherResponse.description
        }
    )
}

fun Long.toTimeFormat(): String{
    val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    val date = Date(this)
   return simpleDateFormat.format(date)
}

fun TemperatureResponse.getAverageTempDisplay(): String{
    return "${(max + min)/2} $CELSIUS_SYMBOL"
}