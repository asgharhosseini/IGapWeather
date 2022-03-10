package ir.ah.igapweather.data.repository.currentweather
import ir.ah.igapweather.data.model.ForecastResponse
import ir.ah.igapweather.data.model.WeatherResponse
import ir.ah.igapweather.data.remote.ApiService
import ir.ah.igapweather.other.util.safeApiCall
import ir.ah.igapweather.other.wrapper.Resource
import javax.inject.Inject

class CurrentWeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    CurrentWeatherRepository {
    override suspend fun getCurrentWeather(localName: String): Resource<WeatherResponse> =
        safeApiCall { apiService.getCurrentWeather(localName) }

    override suspend fun getForecastWeather(localName: String): Resource<ForecastResponse> {
        return safeApiCall { apiService.getForecastWeather(localName) }
    }

    override suspend fun getNextWeather(localName: String): Resource<ForecastResponse> {
        return safeApiCall { apiService.getNextWeather(localName) }
    }


}