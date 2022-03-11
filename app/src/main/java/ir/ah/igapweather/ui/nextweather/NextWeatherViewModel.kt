package ir.ah.igapweather.ui.nextweather
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ah.igapweather.base.BaseViewModel
import ir.ah.igapweather.data.model.ForecastResponse
import ir.ah.igapweather.data.repository.nextWeather.NextWeatherRepository
import ir.ah.igapweather.other.wrapper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class NextWeatherViewModel @Inject constructor(
    private val mainCoroutineDispatcher: CoroutineDispatcher,
    private val repository: NextWeatherRepository,
) : BaseViewModel(mainCoroutineDispatcher) {
    private val nextWeatherForecastChanel = Channel<Resource<ForecastResponse>>()
    val nextWeatherForecast = nextWeatherForecastChanel.receiveAsFlow()


    fun getNextWeather() = doInMain {
        nextWeatherForecastChanel.send(Resource.Loading)
        nextWeatherForecastChanel.send(
            repository.getNextWeather( )
        )

    }

}