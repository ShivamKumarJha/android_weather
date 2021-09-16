package com.shivamkumarjha.openweathermap.network

import com.shivamkumarjha.openweathermap.config.Constants
import com.shivamkumarjha.openweathermap.model.ForecastMain
import com.shivamkumarjha.openweathermap.model.WeatherMain
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") q: String = Constants.DEFAULT_LOCATION,
        @Query("APPID") appId: String = Constants.APP_ID
    ): Response<WeatherMain>

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") q: String = Constants.DEFAULT_LOCATION,
        @Query("APPID") appId: String = Constants.APP_ID
    ): Response<ForecastMain>

}