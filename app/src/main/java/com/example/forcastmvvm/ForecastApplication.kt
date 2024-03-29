package com.example.forcastmvvm

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.forcastmvvm.data.db.ForecastDatabase
import com.example.forcastmvvm.data.network.*
import com.example.forcastmvvm.data.provider.LocationProvider
import com.example.forcastmvvm.data.provider.LocationProviderImpl
import com.example.forcastmvvm.data.provider.UnitProvider
import com.example.forcastmvvm.data.provider.UnitProviderImpl
import com.example.forcastmvvm.data.repository.ForecastRepository
import com.example.forcastmvvm.data.repository.ForecastRepositoryImpl
import com.example.forcastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl() }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance(), instance() ,instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false)
    }

}