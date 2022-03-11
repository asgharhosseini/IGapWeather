package ir.ah.igapweather.data.repository.nextWeather

import ir.ah.igapweather.data.model.ForecastResponse
import ir.ah.igapweather.other.wrapper.Resource


interface NextWeatherRepository {
    suspend fun getNextWeather(
        localName: String ="tehran",
    ):Resource<ForecastResponse>
}