package com.shivamkumarjha.openweathermap.ui.main

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.openweathermap.R
import com.shivamkumarjha.openweathermap.databinding.ActivityMainBinding
import com.shivamkumarjha.openweathermap.network.Resource
import com.shivamkumarjha.openweathermap.ui.extensions.getColorById
import com.shivamkumarjha.openweathermap.ui.main.adapter.ForecastAdapter
import com.shivamkumarjha.openweathermap.utility.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //Views
    private lateinit var binding: ActivityMainBinding
    private lateinit var slideUpAnimation: Animation
    private lateinit var forecastAdapter: ForecastAdapter

    //ViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViews()
        observer()
    }

    private fun setViews() {
        binding.buttonRetry.setBackgroundColor(getColorById(R.color.bg_button))
        binding.buttonRetry.setOnClickListener {
            binding.constraintLayoutId.setBackgroundColor(getColorById(R.color.bg_success))
            binding.buttonRetry.isVisible = false
            binding.tvErrorId.isVisible = false
            viewModel.callApi()
        }

        slideUpAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)

        //Recycler view
        forecastAdapter = ForecastAdapter()
        forecastAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        val mLayoutManager = LinearLayoutManager(this)
        binding.recyclerViewWeather.apply {
            setHasFixedSize(true)
            adapter = forecastAdapter
            layoutManager = mLayoutManager
        }
    }

    private fun observer() {
        viewModel.weather.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Success<*> -> {
                        statusToggle(true)
                        it.data?.let { weather ->
                            binding.tvCityId.text = weather.name
                            binding.tvTempId.text = getString(
                                R.string.temperature_value,
                                Utility.convertKelvinToCelsius(weather.main.temp).toInt().toString()
                            )
                        }
                    }
                    is Resource.Error<*> -> statusToggle(false)
                    is Resource.Loading<*> -> {
                    }
                    is Resource.Offline<*> -> statusToggle(false)
                }
                binding.progressBarId.isVisible = it is Resource.Loading
            }
        }

        viewModel.forecast.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Success<*> -> {
                        statusToggle(true)
                        it.data?.let { forecast ->
                            forecastAdapter.setForecasts(Utility.getForecasts(forecast.list))
                        }
                    }
                    is Resource.Error<*> -> statusToggle(false)
                    is Resource.Loading<*> -> {
                    }
                    is Resource.Offline<*> -> statusToggle(false)
                }
                binding.progressBarId.isVisible = it is Resource.Loading
            }
        }
    }

    private fun statusToggle(isSuccess: Boolean) {
        binding.tvTempId.isVisible = isSuccess
        binding.tvCityId.isVisible = isSuccess
        binding.recyclerViewWeather.isVisible = isSuccess
        binding.tvErrorId.isVisible = !isSuccess
        binding.buttonRetry.isVisible = !isSuccess
        if (isSuccess) {
            binding.recyclerViewWeather.startAnimation(slideUpAnimation)
        } else {
            binding.constraintLayoutId.setBackgroundColor(getColorById(R.color.bg_failure))
        }
    }

}