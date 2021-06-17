package com.nab.domain.usecases

import com.nab.domain.repositories.IForecastRepository
import javax.inject.Inject

/**
 * Created by tho nguyen on 6/15/2021.
 * dinhthohcmus@gmail.com
 */

interface IRemoveWeatherInfoLocalUseCase {
    suspend fun clearWeatherInfoLocal()
}


class RemoveWeatherInfoLocalUseCase @Inject constructor(private val repository: IForecastRepository) :
    IRemoveWeatherInfoLocalUseCase {
    override suspend fun clearWeatherInfoLocal() {
        repository.clearWeatherInfoLocal()
    }

}