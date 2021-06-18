package com.nab.data.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nab.data.entities.TemperatureResponse
import com.nab.data.entities.WeatherInfoRawResponse
import com.nab.data.entities.WeatherResponse
import com.nab.data.entities.createSampleLocalInfo
import com.nab.data.repositories.rawWeatherInfo
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by tho nguyen on 6/18/2021.
 * dinhthohcmus@gmail.com
 */

@RunWith(MockitoJUnitRunner::class)
class NetworkAndLocalModelMapperTest {

    @Test
    fun `verify mapper from WeatherInfoRawResponse to LocalWeatherForecastInfo`() {
        val weatherInfoRawResponse =
            Gson().fromJson(rawWeatherInfo, WeatherInfoRawResponse::class.java)
        val localData = weatherInfoRawResponse.toLocalWeatherInfo("Saigon")
        weatherInfoRawResponse.apply {
            assert(dt == localData.dt)
            assert(temp.toJson() == localData.temp)
            assert(pressure == localData.pressure)
            assert(humidity == localData.humidity)
            assert(weather.toJson() == localData.weather)
        }
    }

    @Test
    fun `verify mapper from LocalWeatherForecastInfo to WeatherInfoRawResponse`() {
        val localWeatherForecastInfo = createSampleLocalInfo()
        val rawInfo = localWeatherForecastInfo.toRawWeatherInfo()
        localWeatherForecastInfo.apply {
            assert(dt == rawInfo.dt)
            assert(Gson().fromJson(temp, TemperatureResponse::class.java) == rawInfo.temp)
            assert(pressure == rawInfo.pressure)
            assert(humidity == rawInfo.humidity)
            assert(
                Gson().fromJson<List<WeatherResponse>>(
                    weather,
                    object : TypeToken<List<WeatherResponse>?>() {}.type
                )
                        == rawInfo.weather
            )


        }
    }
}