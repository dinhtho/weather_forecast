package com.nab.weatherforecast.di

import com.nab.data.repository.ForecastRepository
import com.nab.domain.repositories.IForecastRepository
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
abstract class MainModule {
    @Binds
    abstract fun bindClearLocalData(impl: ForecastRepository): IForecastRepository


}

