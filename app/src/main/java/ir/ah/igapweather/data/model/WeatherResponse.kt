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
      DayOfWeek.MONDAY -> Color.parseColor("#28E0AE")
      DayOfWeek.TUESDAY -> Color.parseColor("#FF0090")
      DayOfWeek.WEDNESDAY -> Color.parseColor("#FFAE00")
      DayOfWeek.THURSDAY -> Color.parseColor("#0090FF")
      DayOfWeek.FRIDAY -> Color.parseColor("#DC0000")
      DayOfWeek.SATURDAY -> Color.parseColor("#0051FF")
      DayOfWeek.SUNDAY -> Color.parseColor("#3D28E0")
      else -> Color.parseColor("#28E0AE")
    }
  }


}