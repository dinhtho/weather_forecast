package com.nab.weatherforecast.forecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nab.weatherforecast.R
import com.nab.weatherforecast.common.SingleClickListener
import com.nab.weatherforecast.common.hideKeyboard
import com.nab.weatherforecast.common.toast
import com.nab.weatherforecast.common.value
import com.nab.weatherforecast.databinding.ActivityMainBinding
import com.nab.weatherforecast.forecast.adapter.WeatherInfoAdapter
import com.nab.weatherforecast.forecast.state.ForecastLoadingState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var weatherInfoAdapter: WeatherInfoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViews()

        setupObserver()

    }

    private fun setupViews() {
        setupRecyclerView()
        setupSearch()
    }

    private fun setupSearch() {
        binding.tvSearch.setOnClickListener(object : SingleClickListener() {
            override fun onClicked(v: View?) {
                hideKeyboard(binding.tvSearch)
                search()
            }
        })
    }

    private fun search() {
        val city = binding.etSearch.text?.trim()
        if (city?.length.value() < 3) {
            toast(getString(R.string.input_at_least_three_characters))
        } else {
            viewModel.getWeatherInfo(city.toString())
        }
    }

    private fun setupObserver() {
        viewModel.getForecastLoadingState().observe(this, {
            when (it) {
                is ForecastLoadingState.Loading -> {
                    binding.flLoading.isVisible = it.loading
                }
                is ForecastLoadingState.Success -> {
                    weatherInfoAdapter?.setDataSource(it.response)
                }
                is ForecastLoadingState.Error -> {
                    toast(it.error.message.value())
                }
            }
        })
    }

    private fun setupRecyclerView() {
        weatherInfoAdapter = WeatherInfoAdapter(layoutInflater)
        binding.rvInfos.apply {
            adapter = weatherInfoAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}