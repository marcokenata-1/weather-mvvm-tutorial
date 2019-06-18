package com.example.forcastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forcastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out UnitSpecificCurrentWeatherEntry>
}