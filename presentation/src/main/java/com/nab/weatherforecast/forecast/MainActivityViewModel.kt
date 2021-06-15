package com.nab.weatherforecast.forecast

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nab.domain.entities.ForecastResult
import com.nab.domain.usecases.IGetWeatherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onErrorCollect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by tho nguyen on 6/15/2021.
 * dinhthohcmus@gmail.com
 */
@HiltViewModel
class MainActivityViewModel
@Inject constructor(private val getWeatherInfoUseCase: IGetWeatherInfoUseCase) : ViewModel() {

    fun getWeatherInfo(cityName: String) {
        viewModelScope.launch {
            getWeatherInfoUseCase.getWeatherInfoDaily(cityName)
                .flowOn(Dispatchers.IO)
                .collect {
                    if (it is ForecastResult.Success) {
                        Log.d("----", "getWeatherInfo: ${it.weatherInfos.size}")
                    } else {
//                        Log.d("----", "getWeatherInfo: ${it.weatherInfos.size}")
                    }
                }
        }
    }
}