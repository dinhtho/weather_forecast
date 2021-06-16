package com.nab.weatherforecast.di

import android.app.Application
import android.content.Context
import com.nab.data.repository.ForecastRepository
import com.nab.domain.repositories.IForecastRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

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

@InstallIn(SingletonComponent::class)
@Module
object MainProvideModule{
    @Provides
    @Singleton
    fun provideApplication(): Context {
        return MainApp.instance
    }
}

