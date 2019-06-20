package com.example.forcastmvvm.data.network.response

import com.example.forcastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.forcastmvvm.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation
)