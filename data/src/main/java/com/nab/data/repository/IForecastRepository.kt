package com.nab.data.repository

import com.nab.data.BuildConfig
import com.nab.data.api.WeatherForecastService
import com.nab.data.mappers.toWeatherInfo
import com.nab.domain.entities.ForecastResult
import com.nab.domain.repositories.IForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by tho nguyen on 6/15/2021.
 * dinhthohcmus@gmail.com
 */
class ForecastRepository @Inject constructor(
    private val weatherForecastService: WeatherForecastService
) :
    IForecastRepository {
    override suspend fun getWeatherInfoDaily(cityName: String): Flow<ForecastResult> {
        return flow {
            val response = weatherForecastService.getWeatherInfoDaily(
                key = cityName,
                appId = BuildConfig.APP_ID
            )
            val infos = response.response.map { it.toWeatherInfo() }
            emit(ForecastResult.Success(infos))
        }
    }

    override suspend fun clearWeatherInfoLocal() {

    }
}