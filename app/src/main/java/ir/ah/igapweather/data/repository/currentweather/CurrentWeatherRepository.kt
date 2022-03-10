package ir.ah.igapweather.data.repository.currentweather

import ir.ah.igapweather.data.model.ForecastResponse
import ir.ah.igapweather.data.model.WeatherResponse
import ir.ah.igapweather.other.wrapper.Resource


interface CurrentWeatherRepository {
    suspend fun getCurrentWeather(
        localName: String = "tehran",
    ): Resource<WeatherResponse>

    suspend fun getForecastWeather(
        localName: String = "tehran",
    ): Resource<ForecastResponse>

    suspend fun getNextWeather(
        localName: String = "tehran",
    ): Resource<ForecastResponse>
}