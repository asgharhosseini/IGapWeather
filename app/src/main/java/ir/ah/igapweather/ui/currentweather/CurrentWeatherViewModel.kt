package ir.ah.igapweather.ui.currentweather

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ah.igapweather.base.BaseViewModel
import ir.ah.igapweather.data.model.ForecastResponse
import ir.ah.igapweather.data.model.WeatherResponse
import ir.ah.igapweather.data.repository.currentweather.CurrentWeatherRepository
import ir.ah.igapweather.other.wrapper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val mainCoroutineDispatcher: CoroutineDispatcher,
    private val repository: CurrentWeatherRepository
) : BaseViewModel(mainCoroutineDispatcher) {

    private val currentWeatherChanel = Channel<Resource<WeatherResponse>>()
    val currentWeather = currentWeatherChanel.receiveAsFlow()
    private val currentForecastChanel = Channel<Resource<ForecastResponse>>()
    val currentForecast = currentForecastChanel.receiveAsFlow()
    private val nextWeatherForecastChanel = Channel<Resource<ForecastResponse>>()
    val nextWeatherForecast = nextWeatherForecastChanel.receiveAsFlow()


    fun getCurrentWeather() = doInMain {
        currentWeatherChanel.send(Resource.Loading)
        currentWeatherChanel.send(
            repository.getCurrentWeather()
        )
    }

    fun getForecastWeather() = doInMain {
        currentForecastChanel.send(Resource.Loading)
        currentForecastChanel.send(
            repository.getForecastWeather()
        )
    }

    fun getNextWeather() = doInMain {
        nextWeatherForecastChanel.send(Resource.Loading)
        nextWeatherForecastChanel.send(
            repository.getNextWeather()
        )

    }


}