package com.nab.domain.repositories

import com.nab.domain.entities.ForecastResult
import kotlinx.coroutines.flow.Flow

interface IForecastRepository {
    suspend fun getWeatherInfoDaily(cityName: String): Flow<ForecastResult>

    suspend fun clearWeatherInfoLocal()

}