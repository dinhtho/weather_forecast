package com.nab.domain

import com.nab.domain.entities.ErrorModel
import com.nab.domain.entities.ForecastResult
import com.nab.domain.entities.WeatherInfoDisplay
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by tho nguyen on 6/18/2021.
 * dinhthohcmus@gmail.com
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetWeatherInfoUseCaseTest {
    @Mock
    private lateinit var repository: IForecastRepository

    private lateinit var getWeatherInfoUseCase: IGetWeatherInfoUseCase


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(GetWeatherInfoUseCaseTest::class)
        getWeatherInfoUseCase = GetWeatherInfoUseCase(repository)
    }


    @Test
    fun `verify get weather information success`() {
        val name = "Saigon"
        runBlockingTest {
            `when`(repository.getWeatherInfoDaily(name)).thenReturn(
                flowOf(ForecastResult.Success(listOf(createSampleWeatherInfo())))
            )
            getWeatherInfoUseCase.getWeatherInfoDaily(name).collect {
                assert(it is ForecastResult.Success)
                assert((it as ForecastResult.Success).weatherInfos.isNotEmpty())
            }
        }
    }


 @Test
    fun `verify get weather information failed`() {
        val name = "Saigon"
        runBlockingTest {
            `when`(repository.getWeatherInfoDaily(name)).thenReturn(
                flowOf(ForecastResult.Failed(ErrorModel("error")))
            )
            getWeatherInfoUseCase.getWeatherInfoDaily(name).collect {
                assert(it is ForecastResult.Failed)
                assert((it as ForecastResult.Failed).errorModel.message.isNullOrEmpty().not())
            }
        }
    }


}