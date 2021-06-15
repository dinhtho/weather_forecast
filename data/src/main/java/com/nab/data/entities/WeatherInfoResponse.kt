package com.nab.data.entities

import com.google.gson.annotations.SerializedName


data class ForecastResponse(
    @SerializedName("cnt") val count: String,
    @SerializedName("list") val response: List<WeatherInfoResponse>
)

class WeatherInfoResponse(
    @SerializedName("dt") val dt: Long,
    @SerializedName("temp") val temp: TemperatureResponse,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("weather") val weather: List<WeatherResponse>
)

class TemperatureResponse(
    @SerializedName("min") val min: Float = 0F,
    @SerializedName("max") val max: Float = 0F,
    @SerializedName("day") val day: Float = 0F,
    @SerializedName("night") val night: Float = 0F,
    @SerializedName("eve") val eve: Float = 0F,
    @SerializedName("morn") val morn: Float = 0F,
)

data class WeatherResponse(
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("main") val main: String? = null,
    @SerializedName("icon") val icon: String? = null
)
