package com.shivamkumarjha.openweathermap.utility

import com.shivamkumarjha.openweathermap.model.Forecast
import com.shivamkumarjha.openweathermap.model.WeatherList
import java.text.SimpleDateFormat
import java.util.*

object Utility {

    fun convertKelvinToCelsius(temperature: Double): Double {
        return temperature - 273.15
    }

    private fun getAverageTemperature(
        temp: Double,
        feels_like: Double,
        temp_min: Double,
        temp_max: Double
    ): Double {
        return (temp + feels_like + temp_min + temp_max) / 4
    }

    private fun getForecastDate(date: String): String {
        return date.substringBefore(" ")
    }

    fun getForecasts(weatherList: ArrayList<WeatherList>): ArrayList<Forecast> {
        var size = 0
        val forecasts: ArrayList<Forecast> = arrayListOf()
        for ((index, item) in weatherList.withIndex()) {
            if (index < weatherList.size - 1) {
                // We need list with only 4 entries
                if (size >= 4)
                    return forecasts
                // Compare current item with next item
                if (getForecastDate(item.dt_txt) == getForecastDate(weatherList[index + 1].dt_txt)) {
                    // Using sub Index to determine weather to add new entry or update previous entry
                    var subIndex = -1
                    if (forecasts.isNotEmpty())
                        for ((i, weather) in forecasts.withIndex()) {
                            if (getForecastDate(weather.day) == getForecastDate(item.dt_txt)) {
                                subIndex = i
                                break
                            }
                        }
                    if (subIndex == -1) {
                        forecasts.add(
                            Forecast(
                                item.dt_txt,
                                getAverageTemperature(
                                    item.main.temp,
                                    item.main.feels_like,
                                    item.main.temp_min,
                                    item.main.temp_max
                                )
                            )
                        )
                        size++
                    } else {
                        // New average temperature of previous + current
                        val averageTemperature = (getAverageTemperature(
                            item.main.temp,
                            item.main.feels_like,
                            item.main.temp_min,
                            item.main.temp_max
                        ) + forecasts[subIndex].temperature) / 2
                        forecasts[subIndex] = Forecast(
                            item.dt_txt,
                            averageTemperature
                        )
                    }
                } else {
                    // Decide to add current item
                    var subIndex = -1
                    if (forecasts.isNotEmpty())
                        for ((i, weather) in forecasts.withIndex()) {
                            if (getForecastDate(weather.day) == getForecastDate(item.dt_txt)) {
                                subIndex = i
                                break
                            }
                        }
                    if (subIndex == -1) {
                        forecasts.add(
                            Forecast(
                                item.dt_txt,
                                getAverageTemperature(
                                    item.main.temp,
                                    item.main.feels_like,
                                    item.main.temp_min,
                                    item.main.temp_max
                                )
                            )
                        )
                        size++
                    }
                }
            }
        }
        return forecasts
    }

    fun getDayFromDate(date: String): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(format.parse(date)!!)
    }
}