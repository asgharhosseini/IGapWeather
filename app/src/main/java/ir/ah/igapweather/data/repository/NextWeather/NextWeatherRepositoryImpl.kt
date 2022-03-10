package ir.ah.igapweather.data.repository.NextWeather
import ir.ah.igapweather.data.model.ForecastResponse
import ir.ah.igapweather.data.model.WeatherResponse
import ir.ah.igapweather.data.remote.ApiService
import ir.ah.igapweather.other.util.safeApiCall
import ir.ah.igapweather.other.wrapper.Resource
import javax.inject.Inject

class NextWeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    NextWeatherRepository {

    override suspend fun getNextWeather(localName: String): Resource<ForecastResponse> {
        return safeApiCall { apiService.getNextWeather(localName) }
    }


}