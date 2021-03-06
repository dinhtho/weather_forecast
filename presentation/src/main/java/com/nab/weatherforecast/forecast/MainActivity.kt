package com.nab.weatherforecast.forecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.crashlytics.internal.common.CommonUtils
import com.nab.weatherforecast.R
import com.nab.weatherforecast.common.*
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
        if (CommonUtils.isRooted(this)) {
            toast(getString(R.string.not_support_rooted_device))
            return
        }

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
                    binding.tvError.isVisible = false
                    weatherInfoAdapter?.setDataSource(it.response)
                }
                is ForecastLoadingState.Error -> {
                    binding.apply {
                        tvError.isVisible = true
                        tvError.text = it.error.message.value()
                    }
                    weatherInfoAdapter?.clearAll()
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