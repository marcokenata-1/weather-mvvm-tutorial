package com.example.forcastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.example.forcastmvvm.data.repository.ForecastRepository
import com.example.forcastmvvm.internal.UnitSystem
import com.example.forcastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC
    val isMetric : Boolean
        get() = unitSystem == UnitSystem.METRIC


    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
