package com.shivamkumarjha.openweathermap.repository

import com.shivamkumarjha.openweathermap.model.ForecastResponse
import com.shivamkumarjha.openweathermap.model.WeatherResponse
import com.shivamkumarjha.openweathermap.network.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(location: String): Flow<Resource<WeatherResponse?>>
    suspend fun getForecast(location: String): Flow<Resource<ForecastResponse?>>
}