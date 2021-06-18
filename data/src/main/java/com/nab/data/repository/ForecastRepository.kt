package com.nab.data.repository

import android.content.Context
import com.nab.data.BuildConfig
import com.nab.data.api.WeatherForecastService
import com.nab.data.common.runWithCatchError
import com.nab.data.entities.LocalWeatherForecastInfo
import com.nab.data.entities.WeatherInfoRawResponse
import com.nab.data.mappers.toLocalWeatherInfo
import com.nab.data.mappers.toRawWeatherInfo
import com.nab.data.mappers.toWeatherInfoDisplay
import com.nab.data.room.WeatherInfoDatabase
import com.nab.domain.entities.ForecastResult
import com.nab.domain.repositories.IForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
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
            val localList = weatherInfoDatabase.weatherInfoDao().getLocalWeatherInfos(cityName)
            if (!localList.isNullOrEmpty()) {
               val response = localList.map { it.toRawWeatherInfo() }
                val listData = response.map { it.toWeatherInfoDisplay() }
                emit(ForecastResult.Success(listData))
            } else {
                runWithCatchError(
                    call = suspend {
                        weatherForecastService.getWeatherInfoDaily(
                            key = cityName,
                            appId = BuildConfig.APP_ID
                        )
                    },
                    success = {
                       val response = it.response
                        saveLocalData(response.map { it.toLocalWeatherInfo(cityName) })
                        val listData = response.map { it.toWeatherInfoDisplay() }
                        emit(ForecastResult.Success(listData))
                    },
                    failed = {
                        emit(it)
                    }
                )
            }
        }
    }

    override suspend fun clearWeatherInfoLocal() {
        weatherInfoDatabase.weatherInfoDao().removeLocalData()
    }

    private suspend fun saveLocalData(List: List<LocalWeatherForecastInfo>) {
        weatherInfoDatabase.weatherInfoDao().saveWeatherInfos(List)
    }
}