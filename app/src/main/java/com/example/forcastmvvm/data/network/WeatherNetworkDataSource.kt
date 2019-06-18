package com.example.forcastmvvm.data.network

import androidx.lifecycle.LiveData
import com.example.forcastmvvm.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather (
        location: String,
        languageCode: String
    )
}