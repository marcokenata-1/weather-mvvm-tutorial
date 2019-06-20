package com.example.forcastmvvm.data.provider

import com.example.forcastmvvm.data.db.entity.WeatherLocation

interface LocationProvider {

    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation) : Boolean
    suspend fun getPreferredLocationString() : String
}