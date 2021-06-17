package com.nab.weatherforecast.forecast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nab.domain.entities.WeatherInfoDisplay
import com.nab.weatherforecast.common.asyncText
import com.nab.weatherforecast.databinding.ItemWeatherInfoBinding
import com.nab.weatherforecast.forecast.adapter.base.BaseAdapter
import com.nab.weatherforecast.forecast.adapter.base.BaseHolder

class WeatherInfoAdapter(private val inflater: LayoutInflater) :
    BaseAdapter<WeatherInfoDisplay, BaseHolder<WeatherInfoDisplay, *>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<WeatherInfoDisplay, *> {
        return WeatherInfoViewHolder(
            ItemWeatherInfoBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun getDiffItem(): SameItemCallBack<WeatherInfoDisplay> {
        return object : SameItemCallBack<WeatherInfoDisplay> {
            override fun areItemsTheSame(
                oldItem: WeatherInfoDisplay,
                newItem: WeatherInfoDisplay
            ): Boolean {
                return oldItem.time == newItem.time
            }

            override fun areContentsTheSame(
                oldItem: WeatherInfoDisplay,
                newItem: WeatherInfoDisplay
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class WeatherInfoViewHolder(viewBinding: ItemWeatherInfoBinding) :
    BaseHolder<WeatherInfoDisplay, ItemWeatherInfoBinding>(viewBinding) {

    override fun bind(data: WeatherInfoDisplay, position: Int) {
        binding.apply {
            tvDate.asyncText(data.time)
            tvTemp.asyncText(data.averageTemperature)
            tvPressure.asyncText(data.pressure)
            tvHumidity.asyncText(data.humidity)
            tvDescription.asyncText(data.description)
        }
    }
}