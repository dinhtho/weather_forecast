package com.nab.domain.di

import com.nab.domain.repositories.IForecastRepository
import com.nab.domain.usecases.ClearWeatherInfoLocalUseCase
import com.nab.domain.usecases.GetWeatherInfoUseCase
import com.nab.domain.usecases.IClearWeatherInfoLocalUseCase
import com.nab.domain.usecases.IGetWeatherInfoUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by tho nguyen on 6/16/2021.
 * dinhthohcmus@gmail.com
 */

@InstallIn(ViewModelComponent::class)
@Module
abstract class DomainModule {
    @Binds
    abstract fun bindGetWeather(impl: GetWeatherInfoUseCase): IGetWeatherInfoUseCase

    @Binds
    abstract fun bindClearLocalData(impl: ClearWeatherInfoLocalUseCase): IClearWeatherInfoLocalUseCase


}