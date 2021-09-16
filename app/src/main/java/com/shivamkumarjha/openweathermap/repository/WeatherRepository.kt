package com.shivamkumarjha.openweathermap.repository

import com.shivamkumarjha.openweathermap.model.ForecastMain
import com.shivamkumarjha.openweathermap.model.WeatherMain
import com.shivamkumarjha.openweathermap.network.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(location: String): Flow<Resource<WeatherMain?>>
    suspend fun getForecast(location: String): Flow<Resource<ForecastMain?>>
}