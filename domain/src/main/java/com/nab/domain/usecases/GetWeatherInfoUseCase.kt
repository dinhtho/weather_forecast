package com.nab.domain.usecases

import com.nab.domain.entities.ForecastResult
import com.nab.domain.repositories.IForecastRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by tho nguyen on 6/15/2021.
 * dinhthohcmus@gmail.com
 */

interface IGetWeatherInfoUseCase {
    suspend fun getWeatherInfoDaily(cityName: String): Flow<ForecastResult>
}


class GetWeatherInfoUseCase @Inject constructor(private val repository: IForecastRepository) :
    IGetWeatherInfoUseCase {
    override suspend fun getWeatherInfoDaily(cityName: String): Flow<ForecastResult> {
       return repository.getWeatherInfoDaily(cityName)
    }
}