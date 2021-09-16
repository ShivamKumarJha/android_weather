package com.shivamkumarjha.openweathermap.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("cod") val cod: Int,
    @SerializedName("message") val message: Int,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("list") val list: ArrayList<WeatherList>,
    @SerializedName("city") val city: City
)