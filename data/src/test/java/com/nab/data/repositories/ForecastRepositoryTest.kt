package com.nab.data.repositories

import android.accounts.NetworkErrorException
import com.google.gson.Gson
import com.nab.data.BuildConfig
import com.nab.data.api.WeatherForecastService
import com.nab.data.entities.ForecastResponse
import com.nab.data.entities.createSampleLocalInfo
import com.nab.data.repository.ForecastRepository
import com.nab.data.room.WeatherInfoDAO
import com.nab.data.room.WeatherInfoDatabase
import com.nab.domain.entities.ErrorModel
import com.nab.domain.entities.ForecastResult
import com.nab.domain.entities.createSampleWeatherInfo
import com.nab.domain.repositories.IForecastRepository
import com.nab.domain.usecases.GetWeatherInfoUseCase
import com.nab.domain.usecases.IGetWeatherInfoUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception
import java.lang.RuntimeException

/**
 * Created by tho nguyen on 6/18/2021.
 * dinhthohcmus@gmail.com
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ForecastRepositoryTest {

    @Mock
    private lateinit var weatherForecastService: WeatherForecastService
    @Mock
    private lateinit var weatherInfoDatabase: WeatherInfoDatabase

    private lateinit var forecastRepository: IForecastRepository


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(ForecastRepositoryTest::class)
        forecastRepository = ForecastRepository(weatherForecastService, weatherInfoDatabase)
        `when`(weatherInfoDatabase.weatherInfoDao()).thenReturn(mock(WeatherInfoDAO::class.java))
    }

    @Test
    fun `verify invoke get data from database success`() {
        runBlockingTest {
            val name = "Saigon"
            `when`(weatherInfoDatabase.weatherInfoDao().getLocalWeatherInfos(name))
                .thenReturn(listOf(createSampleLocalInfo()))
            forecastRepository.getWeatherInfoDaily(name).collect {
                assert(it is ForecastResult.Success)
                assert((it as ForecastResult.Success).weatherInfos.isNotEmpty())
            }
        }
    }

    @Test
    fun `verify invoke get data from remote success`() {
        runBlockingTest {
            val name = "Saigon"
            `when`(weatherInfoDatabase.weatherInfoDao().getLocalWeatherInfos(name))
                .thenReturn(listOf())
            `when`(weatherForecastService.getWeatherInfoDaily(name, appId = BuildConfig.APP_ID))
                .thenReturn(Gson().fromJson(responseJson, ForecastResponse::class.java))

            forecastRepository.getWeatherInfoDaily(name).collect {
                assert(it is ForecastResult.Success)
                assert((it as ForecastResult.Success).weatherInfos.size == 7)
            }
        }
    }


    @Test
    fun `verify invoke get data failed`() {
        runBlockingTest {
            val name = "Saigon"
            `when`(weatherInfoDatabase.weatherInfoDao().getLocalWeatherInfos(name))
                .thenReturn(listOf())
            `when`(weatherForecastService.getWeatherInfoDaily(name, appId = BuildConfig.APP_ID))
                .thenAnswer { throw NetworkErrorException() }

            forecastRepository.getWeatherInfoDaily(name).collect {
                assert(it is ForecastResult.Failed)
                assert((it as ForecastResult.Failed).errorModel.message.isNullOrEmpty().not())
            }
        }
    }


}