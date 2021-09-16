package com.shivamkumarjha.openweathermap.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.openweathermap.R
import com.shivamkumarjha.openweathermap.databinding.ItemForecastBinding
import com.shivamkumarjha.openweathermap.model.Forecast
import com.shivamkumarjha.openweathermap.utility.Utility

class ForecastViewHolder(binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {

    private val day = binding.tvDayId
    private val temperature = binding.tvTempId

    fun initialize(forecast: Forecast) {
        day.text = Utility.getDayFromDate(forecast.day)
        temperature.text = temperature.context.getString(
            R.string.celsius_value,
            Utility.convertKelvinToCelsius(forecast.temperature).toInt().toString()
        )
    }
}