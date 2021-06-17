package com.nab.weatherforecast.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nab.domain.entities.ErrorModel
import com.nab.domain.entities.ForecastResult
import com.nab.domain.usecases.IGetWeatherInfoUseCase
import com.nab.domain.usecases.IRemoveWeatherInfoLocalUseCase
import com.nab.weatherforecast.common.SecuredLocalStore
import com.nab.weatherforecast.forecast.MainActivityViewModel
import com.nab.weatherforecast.forecast.state.ForecastLoadingState
import com.nab.weatherforecast.view_model.common.MainCoroutineRule
import com.nab.weatherforecast.view_model.common.getOrAwaitValue
import com.nab.weatherforecast.view_model.common.invokeSuspendMethod
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mainCoroutineRule = MainCoroutineRule()


    private lateinit var mainActivityViewModel: MainActivityViewModel

    @Mock
    private lateinit var getWeatherInfoUseCase: IGetWeatherInfoUseCase

    @Mock
    private lateinit var removeWeatherInfoLocalUseCase: IRemoveWeatherInfoLocalUseCase

    @Mock
    private lateinit var localStore: SecuredLocalStore

    @Mock
    private lateinit var loadDataObserver: Observer<ForecastLoadingState>


    @Before
    fun init() {
        MockitoAnnotations.openMocks(MainActivityViewModelTest::class)

        mainActivityViewModel = MainActivityViewModel(
            getWeatherInfoUseCase = getWeatherInfoUseCase,
            removeWeatherInfoLocalUseCase = removeWeatherInfoLocalUseCase,
            localStore
        )
    }

    @Test
    fun `verify local data is removed when open app in a new day`() {
        runBlockingTest {
            `when`(localStore.checkRequestTimeOverDate()).thenReturn(true)
            invokeSuspendMethod(mainActivityViewModel, "removeLocalDataIfOnNewDate")
            verify(removeWeatherInfoLocalUseCase, times(1)).clearWeatherInfoLocal()
        }
    }

    @Test
    fun `verify local data is kept when open app in a day`() {
        runBlockingTest {
            `when`(localStore.checkRequestTimeOverDate()).thenReturn(false)
            invokeSuspendMethod(mainActivityViewModel, "removeLocalDataIfOnNewDate")
            verify(removeWeatherInfoLocalUseCase, times(0)).clearWeatherInfoLocal()
        }
    }

    @Test
    fun `verify get weather information success`() {
        mainActivityViewModel.getForecastLoadingState().observeForever(loadDataObserver)
        runBlockingTest {
            val cityName = "Saigon"
            `when`(localStore.checkRequestTimeOverDate()).thenReturn(true)
            `when`(getWeatherInfoUseCase.getWeatherInfoDaily(cityName)).thenReturn(
                flowOf(ForecastResult.Success(listOf()))
            )
            mainActivityViewModel.getWeatherInfo(cityName)
            verify(loadDataObserver, times(2)).onChanged(any(ForecastLoadingState.Loading::class.java))
            verify(getWeatherInfoUseCase).getWeatherInfoDaily(cityName)
            verify(loadDataObserver).onChanged(any(ForecastLoadingState.Success::class.java))
            mainActivityViewModel.getForecastLoadingState().removeObserver(loadDataObserver)
        }
    }

    @Test
    fun `verify get weather information error`() {
        mainActivityViewModel.getForecastLoadingState().observeForever(loadDataObserver)
        runBlockingTest {
            val cityName = "Saigon"
            `when`(localStore.checkRequestTimeOverDate()).thenReturn(true)
            `when`(getWeatherInfoUseCase.getWeatherInfoDaily(cityName)).thenReturn(
                flowOf(ForecastResult.Failed(ErrorModel()))
            )
            mainActivityViewModel.getWeatherInfo(cityName)
            verify(loadDataObserver, times(2)).onChanged(any(ForecastLoadingState.Loading::class.java))
            verify(getWeatherInfoUseCase).getWeatherInfoDaily(cityName)
            verify(loadDataObserver).onChanged(any(ForecastLoadingState.Error::class.java))
            mainActivityViewModel.getForecastLoadingState().removeObserver(loadDataObserver)
        }
    }

}