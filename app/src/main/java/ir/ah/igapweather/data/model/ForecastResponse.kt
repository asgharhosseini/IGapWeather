package ir.ah.igapweather.data.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastResponse(
    @Json(name = "city")
    val city: City?,
    @Json(name = "cnt")
    val cnt: Int?,
    @Json(name = "cod")
    val cod: String?,
    @Json(name = "list")
    val dayWeather: List<DayWeather>?,
    @Json(name = "message")
    val message: Double?
)