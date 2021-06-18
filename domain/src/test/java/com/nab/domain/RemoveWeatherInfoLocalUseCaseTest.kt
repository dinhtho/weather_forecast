package com.nab.domain

import com.nab.domain.repositories.IForecastRepository
import com.nab.domain.usecases.IRemoveWeatherInfoLocalUseCase
import com.nab.domain.usecases.RemoveWeatherInfoLocalUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by tho nguyen on 6/18/2021.
 * dinhthohcmus@gmail.com
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RemoveWeatherInfoLocalUseCaseTest {
    @Mock
    private lateinit var repository: IForecastRepository

    private lateinit var removeWeatherInfoLocalUseCase: IRemoveWeatherInfoLocalUseCase


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(RemoveWeatherInfoLocalUseCaseTest::class)
        removeWeatherInfoLocalUseCase = RemoveWeatherInfoLocalUseCase(repository)
    }

    @Test
    fun `verify invoke remove local data`() {
        runBlockingTest {
            removeWeatherInfoLocalUseCase.clearWeatherInfoLocal()
            verify(repository).clearWeatherInfoLocal()
        }
    }
}