package com.nab.data.api

import com.nab.data.entities.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastService {

    @GET("data/2.5/forecast/daily")
    suspend fun getWeatherInfoDaily(@Query("q") key: String,
                                              @Query("cnt") period: Int = 7,
                                              @Query("appId") appId : String,
                                              @Query("units") units: String = TempUnit.CEL.value
    ) : ForecastResponse
}