package com.nab.data.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nab.data.entities.*


/**
 * Created by tho nguyen on 6/16/2021.
 * dinhthohcmus@gmail.com
 */


fun WeatherInfoRawResponse.toLocalWeatherInfo(cityName: String): LocalWeatherForecastInfo {
    return LocalWeatherForecastInfo(
        cityName = cityName,
        dt = dt,
        temp = temp.toJson(),
        humidity = humidity,
        pressure = pressure,
        weather = weather.toJson()
    )
}

fun LocalWeatherForecastInfo.toRawWeatherInfo(): WeatherInfoRawResponse {
    return WeatherInfoRawResponse(
        dt = dt,
        temp = Gson().fromJson(temp, TemperatureResponse::class.java),
        humidity = humidity,
        pressure = pressure,
        weather = Gson().fromJson(weather, object : TypeToken<List<WeatherResponse>?>() {}.type)
    )
}

fun Any.toJson():String{
   return Gson().toJson(this)
}