package com.nab.domain.entities

/**
 * Created by tho nguyen on 6/15/2021.
 * dinhthohcmus@gmail.com
 */
sealed class ForecastResult {
    class Success(val weatherInfos: List<WeatherInfo>) : ForecastResult()
    class Failed(val errorModel: ErrorModel) : ForecastResult()
}