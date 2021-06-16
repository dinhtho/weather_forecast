package com.nab.data.di

import android.content.Context
import com.nab.data.room.WeatherInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by tho nguyen on 6/16/2021.
 * dinhthohcmus@gmail.com
 */

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : WeatherInfoDatabase {
        return WeatherInfoDatabase.buildDatabase(context)
    }
}