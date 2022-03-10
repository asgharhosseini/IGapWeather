package ir.ah.igapweather.data.remote
import ir.ah.igapweather.data.model.ForecastResponse
import ir.ah.igapweather.data.model.WeatherResponse
import ir.ah.igapweather.di.EndPoint
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(EndPoint.getCheckValidLocalName)
    suspend fun getCheckValidLocalName(
        @Query("q")
        localName: String,
        @Query("appid")
        apiKey: String = EndPoint.apiKey,
        @Query("lang")
        lang: String = EndPoint.lang,
        @Query("units")
        units: String = EndPoint.units
    ): Response<WeatherResponse>

    @GET(EndPoint.getCurrentWeather)
    suspend fun getCurrentWeather(
        @Query("q")
        localName: String="tehran",
        @Query("appid")
        apiKey: String = EndPoint.apiKey,
        @Query("lang")
        lang: String = EndPoint.lang,
        @Query("units")
        units: String = EndPoint.units
    ): Response<WeatherResponse>


    @GET(EndPoint.getForecastWeather)
    suspend fun getForecastWeather(
        @Query("q")
        localName: String="tehran",
        @Query("appid")
        apiKey: String = EndPoint.apiKey,
        @Query("lang")
        lang: String = EndPoint.lang,
        @Query("units")
        units: String = EndPoint.units,
        @Query("cnt")
        cnt: Int = EndPoint.cnt
    ): Response<ForecastResponse>

    @GET(EndPoint.getForecastWeather)
    suspend fun getNextWeather(
        @Query("q")
        localName: String="tehran",
        @Query("appid")
        apiKey: String = EndPoint.apiKey,
        @Query("lang")
        lang: String = EndPoint.lang,
        @Query("units")
        units: String = EndPoint.units,
    ): Response<ForecastResponse>

}