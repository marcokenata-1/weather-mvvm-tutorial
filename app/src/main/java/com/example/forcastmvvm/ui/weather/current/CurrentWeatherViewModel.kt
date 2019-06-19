package com.example.forcastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.example.forcastmvvm.data.provider.UnitProvider
import com.example.forcastmvvm.data.repository.ForecastRepository
import com.example.forcastmvvm.internal.UnitSystem
import com.example.forcastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()
    val isMetric : Boolean
        get() = unitSystem == UnitSystem.METRIC


    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
