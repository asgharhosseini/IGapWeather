package ir.ah.igapweather.data.model

import android.graphics.Color
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class WeatherResponse(
  @Json(name ="base")
    val base: String,
  @Json(name ="clouds")
    val clouds: Clouds,
  @Json(name ="cod")
    val cod: Int,
  @Json(name ="coord")
    val coord: Coord,
  @Json(name ="dt")
    val dt: Long,
  @Json(name ="id")
    val id: Int,
  @Json(name ="main")
    val main: Main,
  @Json(name ="name")
    val name: String,
  @Json(name ="sys")
    val sys: Sys,
  @Json(name ="timezone")
    val timezone: Int,
  @Json(name ="visibility")
    val visibility: Int,
  @Json(name ="weather")
    val weather: List<Weather>,
  @Json(name ="wind")
    val wind: Wind
){
  fun getWeatherItem(): Weather? {
    return weather?.first()
  }

  fun getDay(): String? {
    return dt?.let { getDateTime(it)?.getDisplayName(TextStyle.FULL, Locale.getDefault()) }
  }

  private fun getDateTime(s: Long): DayOfWeek? {
    return try {
      val sdf = SimpleDateFormat("dd/MM/yyyy")
      val netDate = Date(s * 1000)
      val formattedDate = sdf.format(netDate)

      LocalDate.of(
        formattedDate.substringAfterLast("/").toInt(),
        formattedDate.substringAfter("/").take(2).toInt(),
        formattedDate.substringBefore("/").toInt()
      )
        .dayOfWeek
    } catch (e: Exception) {
      e.printStackTrace()
      DayOfWeek.MONDAY
    }
  }

  fun getColor(): Int {
    return when (dt?.let {
      getDateTime(it)
    }) {
      DayOfWeek.MONDAY -> Color.parseColor("#FF5722")
      DayOfWeek.TUESDAY -> Color.parseColor("#FF0090")
      DayOfWeek.WEDNESDAY -> Color.parseColor("#FFCA28")
      DayOfWeek.THURSDAY -> Color.parseColor("#F44336")
      DayOfWeek.FRIDAY -> Color.parseColor("#9C27B0")
      DayOfWeek.SATURDAY -> Color.parseColor("#7CB342")
      DayOfWeek.SUNDAY -> Color.parseColor("#03A9F4")
      else -> Color.parseColor("#2196F3")
    }
  }


}