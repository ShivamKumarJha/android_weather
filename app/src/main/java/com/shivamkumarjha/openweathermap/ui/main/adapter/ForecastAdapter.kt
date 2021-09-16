package com.shivamkumarjha.openweathermap.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.openweathermap.databinding.ItemForecastBinding
import com.shivamkumarjha.openweathermap.model.ForecastModel

class ForecastAdapter : RecyclerView.Adapter<ForecastViewHolder>() {
    private var forecasts: ArrayList<ForecastModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding =
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.initialize(forecasts[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setForecasts(forecasts: ArrayList<ForecastModel>) {
        this.forecasts = forecasts
        notifyDataSetChanged()
    }
}