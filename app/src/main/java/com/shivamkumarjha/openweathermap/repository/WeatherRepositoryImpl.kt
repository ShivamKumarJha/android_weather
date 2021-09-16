package com.shivamkumarjha.openweathermap.repository

import android.util.Log
import com.shivamkumarjha.openweathermap.config.Constants
import com.shivamkumarjha.openweathermap.di.CoroutineDispatchers
import com.shivamkumarjha.openweathermap.model.ForecastMain
import com.shivamkumarjha.openweathermap.model.WeatherMain
import com.shivamkumarjha.openweathermap.network.ApiService
import com.shivamkumarjha.openweathermap.network.NoConnectivityException
import com.shivamkumarjha.openweathermap.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepositoryImpl(
    private val apiService: ApiService,
    private val dispatchers: CoroutineDispatchers
) : WeatherRepository {

    override suspend fun getWeather(location: String): Flow<Resource<WeatherMain?>> = flow {
        emit(Resource.loading(data = null))
        try {
            val response = apiService.getWeather(location)
            if (response.isSuccessful) {
                emit(Resource.success(data = response.body()))
                Log.d(Constants.TAG, response.body().toString())
            } else {
                emit(Resource.error(data = null, message = response.code().toString()))
                Log.d(Constants.TAG, response.code().toString())
            }
        } catch (exception: Exception) {
            if (exception is NoConnectivityException)
                emit(Resource.offline(data = null))
            else {
                emit(Resource.error(data = null, message = exception.message.toString()))
                Log.e(Constants.TAG, exception.message.toString())
            }
        }
    }.flowOn(dispatchers.io)

    override suspend fun getForecast(location: String): Flow<Resource<ForecastMain?>> = flow {
        emit(Resource.loading(data = null))
        try {
            val response = apiService.getForecast(location)
            if (response.isSuccessful) {
                emit(Resource.success(data = response.body()))
                Log.d(Constants.TAG, response.body().toString())
            } else {
                emit(Resource.error(data = null, message = response.code().toString()))
                Log.d(Constants.TAG, response.code().toString())
            }
        } catch (exception: Exception) {
            if (exception is NoConnectivityException)
                emit(Resource.offline(data = null))
            else {
                emit(Resource.error(data = null, message = exception.message.toString()))
                Log.e(Constants.TAG, exception.message.toString())
            }
        }
    }.flowOn(dispatchers.io)

}