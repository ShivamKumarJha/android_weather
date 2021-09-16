package com.shivamkumarjha.openweathermap.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamkumarjha.openweathermap.config.Constants
import com.shivamkumarjha.openweathermap.di.CoroutineDispatchers
import com.shivamkumarjha.openweathermap.model.ForecastMain
import com.shivamkumarjha.openweathermap.model.WeatherMain
import com.shivamkumarjha.openweathermap.network.Resource
import com.shivamkumarjha.openweathermap.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weather = MutableLiveData<Resource<WeatherMain?>>()
    val weather: LiveData<Resource<WeatherMain?>> = _weather

    private val _forecast = MutableLiveData<Resource<ForecastMain?>>()
    val forecast: LiveData<Resource<ForecastMain?>> = _forecast

    init {
        callApi()
    }

    fun callApi(location: String = Constants.DEFAULT_LOCATION) {
        viewModelScope.launch(dispatchers.io) {
            weatherRepository.getWeather(location).collect {
                _weather.postValue(it)
            }
        }
        viewModelScope.launch(dispatchers.io) {
            weatherRepository.getForecast(location).collect {
                _forecast.postValue(it)
            }
        }
    }
}