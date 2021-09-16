package com.shivamkumarjha.openweathermap.di

import com.shivamkumarjha.openweathermap.network.ApiService
import com.shivamkumarjha.openweathermap.repository.WeatherRepository
import com.shivamkumarjha.openweathermap.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun getWeatherRepository(
        apiService: ApiService,
        dispatchers: CoroutineDispatchers
    ): WeatherRepository {
        return WeatherRepositoryImpl(apiService, dispatchers)
    }
}