package ir.ah.igapweather.di

import ir.ah.igapweather.BuildConfig
import ir.ah.igapweather.di.EndPoint.API_PRODUCTION_URL
import ir.ah.igapweather.di.EndPoint.STAGE_BASE_URL


object EndPoint {

    const val STAGE_BASE_URL = BuildConfig.STAGE_BASE_URL
    const val API_PRODUCTION_URL = BuildConfig.API_PRODUCTION_URL
    const val API_IMAGE_URL = "http://openweathermap.org/img/wn/"
    const val getCheckValidLocalName = "weather"
    const val getCurrentWeather = "weather"
    const val getForecastWeather = "forecast"
    const val apiKey = BuildConfig.API_KEY
    const val lang = "fa"
    const val units = "metric"
    const val cnt = 5


}