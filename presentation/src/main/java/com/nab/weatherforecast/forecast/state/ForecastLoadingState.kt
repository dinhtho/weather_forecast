package com.nab.weatherforecast.forecast.state

import com.nab.domain.entities.ErrorModel
import com.nab.domain.entities.WeatherInfoDisplay

/**
 * Created by tho nguyen on 6/17/2021.
 * dinhthohcmus@gmail.com
 */
sealed class ForecastLoadingState {
    class Loading(val loading: Boolean) : ForecastLoadingState()
    class Success(val response: List<WeatherInfoDisplay>) : ForecastLoadingState()
    class Error(val error: ErrorModel) : ForecastLoadingState()
}