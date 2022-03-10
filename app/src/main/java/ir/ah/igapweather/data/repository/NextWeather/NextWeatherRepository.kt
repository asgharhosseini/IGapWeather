package ir.ah.igapweather.data.repository.NextWeather

import ir.ah.igapweather.data.model.ForecastResponse
import ir.ah.igapweather.data.model.WeatherResponse
import ir.ah.igapweather.other.wrapper.Resource


interface NextWeatherRepository {
    suspend fun getNextWeather(
        localName: String ="tehran",
    ):Resource<ForecastResponse>
}