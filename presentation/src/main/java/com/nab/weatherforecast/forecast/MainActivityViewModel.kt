package com.nab.weatherforecast.forecast

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nab.domain.entities.ForecastResult
import com.nab.domain.usecases.IRemoveWeatherInfoLocalUseCase
import com.nab.domain.usecases.IGetWeatherInfoUseCase
import com.nab.weatherforecast.common.SecuredLocalStore
import com.nab.weatherforecast.forecast.state.ForecastLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by tho nguyen on 6/15/2021.
 * dinhthohcmus@gmail.com
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getWeatherInfoUseCase: IGetWeatherInfoUseCase,
    private val removeWeatherInfoLocalUseCase: IRemoveWeatherInfoLocalUseCase,
    private val localStore: SecuredLocalStore
) : ViewModel() {

    private val weatherInfoLoadingState = MutableLiveData<ForecastLoadingState>()
    internal fun getForecastLoadingState(): LiveData<ForecastLoadingState> = weatherInfoLoadingState

    fun getWeatherInfo(cityName: String) {
        weatherInfoLoadingState.value = ForecastLoadingState.Loading(true)
        viewModelScope.launch {
            removeLocalDataIfOnNewDate()
            getWeatherInfoUseCase.getWeatherInfoDaily(cityName)
                .flowOn(Dispatchers.IO)
                .collect {
                    withContext(Dispatchers.Main) {
                        weatherInfoLoadingState.value = ForecastLoadingState.Loading(false)
                        when (it) {
                            is ForecastResult.Success -> {
                                weatherInfoLoadingState.value =(ForecastLoadingState.Success(it.weatherInfos))
                            }
                            is ForecastResult.Failed -> {
                                weatherInfoLoadingState.value = ForecastLoadingState.Error(it.errorModel)
                            }
                        }
                    }
                }
        }
    }

    private suspend fun removeLocalDataIfOnNewDate() {
        if (localStore.checkRequestTimeOverDate()) {
            removeWeatherInfoLocalUseCase.clearWeatherInfoLocal()
        }
    }
}