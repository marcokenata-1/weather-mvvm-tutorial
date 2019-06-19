package com.example.forcastmvvm.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.forcastmvvm.R
import com.example.forcastmvvm.data.network.ApixuWeatherApiService
import com.example.forcastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forcastmvvm.data.network.WeatherNetworkDataSource
import com.example.forcastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.forcastmvvm.internal.glide.GlideApp
import com.example.forcastmvvm.ui.base.ScopeFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopeFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory : CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this , viewModelFactory).get(CurrentWeatherViewModel::class.java)

        bindUI()

    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateLocation("Depok")
            updateDateToToday()
            updateTemperatures(it.temperature,it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection,it.windSpeed)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(imageView_condition_icon)
        })
    }

    private fun chooseLocal(metric: String,imperial : String) : String {
        return if (viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location: String){
        (activity as AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temperature : Double, feelsLike : Double){
        val unitAbbreviation = chooseLocal("°C","°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: String){
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double){
        val unitAbbreviation = chooseLocal("mm","in")
        textView_precipitation.text = "Precipitation : $precipitationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection : String, windSpeed : Double){
        val unitAbbreviation = chooseLocal("kph","mph")
        textView_precipitation.text = "Wind : $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double){
        val unitAbbreviation = chooseLocal("km","mi.")
        textView_precipitation.text = "Visibility : $visibilityDistance $unitAbbreviation"
    }
}
