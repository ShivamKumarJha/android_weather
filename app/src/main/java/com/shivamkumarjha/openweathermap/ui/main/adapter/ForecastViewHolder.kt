package com.shivamkumarjha.openweathermap.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.openweathermap.R
import com.shivamkumarjha.openweathermap.databinding.ItemForecastBinding
import com.shivamkumarjha.openweathermap.model.ForecastModel
import com.shivamkumarjha.openweathermap.utility.Utility

class ForecastViewHolder(binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {

    private val day = binding.tvDayId
    private val temperature = binding.tvTempId

    fun initialize(forecastModel: ForecastModel) {
        day.text = Utility.getDayFromDate(forecastModel.day)
        temperature.text = temperature.context.getString(
            R.string.temperature_value,
            Utility.convertKelvinToCelsius(forecastModel.temperature).toInt().toString()
        )
    }
}