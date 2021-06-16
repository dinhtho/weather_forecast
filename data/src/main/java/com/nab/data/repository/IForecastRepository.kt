package com.nab.data.repository

import com.nab.data.BuildConfig
import com.nab.data.api.WeatherForecastService
import com.nab.data.entities.LocalWeatherForecastInfo
import com.nab.data.entities.WeatherInfoRawResponse
import com.nab.data.mappers.toLocalWeatherInfo
import com.nab.data.mappers.toRawWeatherInfo
import com.nab.data.mappers.toWeatherInfoDisplay
import com.nab.data.room.WeatherInfoDatabase
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
    private val weatherForecastService: WeatherForecastService,
    private val weatherInfoDatabase: WeatherInfoDatabase
) :
    IForecastRepository {
    override suspend fun getWeatherInfoDaily(cityName: String): Flow<ForecastResult> {
        return flow {
            val rawInfos: List<WeatherInfoRawResponse>?
            val localInfos = weatherInfoDatabase.weatherInfoDao().getLocalWeatherInfos(cityName)
            if (!localInfos.isNullOrEmpty()) {
                rawInfos = localInfos.map { it.toRawWeatherInfo() }
            } else {
                val response = weatherForecastService.getWeatherInfoDaily(
                    key = cityName,
                    appId = BuildConfig.APP_ID
                )
                rawInfos = response.response
                saveLocalData(rawInfos.map { it.toLocalWeatherInfo(cityName) })
            }
            val infos = rawInfos.map { it.toWeatherInfoDisplay() }
            emit(ForecastResult.Success(infos))
        }
    }

    override suspend fun clearWeatherInfoLocal() {
        weatherInfoDatabase.weatherInfoDao().removeLocalData()
    }

    private suspend fun saveLocalData(infos: List<LocalWeatherForecastInfo>){
        weatherInfoDatabase.weatherInfoDao().saveWeatherInfos(infos)
    }
}