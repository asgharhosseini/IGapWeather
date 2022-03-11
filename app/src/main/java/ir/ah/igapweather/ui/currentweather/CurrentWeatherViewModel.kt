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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val mainCoroutineDispatcher: CoroutineDispatcher,
    private val repository: CurrentWeatherRepository
) : BaseViewModel(mainCoroutineDispatcher) {
    val searchQuery: MutableStateFlow<String> = MutableStateFlow("tehran")

    private val currentWeatherChanel = Channel<Resource<WeatherResponse>>()
    val currentWeather = currentWeatherChanel.receiveAsFlow()
    private val currentForecastChanel = Channel<Resource<ForecastResponse>>()
    val currentForecast = currentForecastChanel.receiveAsFlow()
    private val nextWeatherForecastChanel = Channel<Resource<ForecastResponse>>()
    val nextWeatherForecast = nextWeatherForecastChanel.receiveAsFlow()
    private val searchEventChanel = Channel<SearchWeatherEvent>()
    private val searchWeatherForecastChanel = Channel<Resource<ForecastResponse>>()
    val searchWeatherForecast = searchWeatherForecastChanel.receiveAsFlow()

    fun validateSearchQuery() {

        val searchQuery = searchQuery.value
        doInMain {
            if (searchQuery.isEmpty()) {
                searchEventChanel.send(SearchWeatherEvent.SearchQueryIsEmpty)
                return@doInMain
            }

            getSearchCurrentWeather()

            return@doInMain
        }
    }

    fun getCurrentWeather() = doInMain {
        currentWeatherChanel.send(Resource.Loading)
        currentWeatherChanel.send(
            repository.getCurrentWeather(searchQuery.value)
        )
    }

    fun getForecastWeather() = doInMain {
        currentForecastChanel.send(Resource.Loading)
        currentForecastChanel.send(
            repository.getForecastWeather(searchQuery.value)
        )
    }

    fun getNextWeather() = doInMain {
        nextWeatherForecastChanel.send(Resource.Loading)
        nextWeatherForecastChanel.send(
            repository.getNextWeather(searchQuery.value)
        )

    }

    private fun getSearchCurrentWeather() = doInMain {
        currentWeatherChanel.send(Resource.Loading)
        currentWeatherChanel.send(
            repository.getSearchCurrentWeather(searchQuery.value)
        )
    }

}