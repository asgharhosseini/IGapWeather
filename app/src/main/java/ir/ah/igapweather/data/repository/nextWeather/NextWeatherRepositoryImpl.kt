package ir.ah.igapweather.data.repository.nextWeather
import ir.ah.igapweather.data.model.ForecastResponse
import ir.ah.igapweather.data.remote.ApiService
import ir.ah.igapweather.other.util.safeApiCall
import ir.ah.igapweather.other.wrapper.Resource
import javax.inject.Inject

/**
 *To receive the server response and convert it to Resource <data class> and send it to a higher layer
 */
class NextWeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    NextWeatherRepository {

    override suspend fun getNextWeather(localName: String): Resource<ForecastResponse> {
        return safeApiCall { apiService.getNextWeather(localName) }
    }


}