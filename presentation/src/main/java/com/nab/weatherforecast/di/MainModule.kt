package com.nab.weatherforecast.di

import com.nab.data.repository.ForecastRepository
import com.nab.domain.repositories.IForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

/**
 * Created by tho nguyen on 6/16/2021.
 * dinhthohcmus@gmail.com
 */

@InstallIn(SingletonComponent::class)
@Module
abstract class MainModule {
    @Binds
    abstract fun bindClearLocalData(impl: ForecastRepository): IForecastRepository


}

