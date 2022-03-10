package ir.ah.igapweather.data.model

import android.graphics.Color
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.threeten.bp.DayOfWeek

class DayWeatherTest {

    @Test
    fun getWeatherItem() {
        val today = getDayWeather()
        assertThat(today.getWeatherItem()).isEqualTo(today.weather[0])
    }

    @Test
    fun getDay() {
        val today = getDayWeather()
        assertThat(today.getDay()).isEqualTo("Saturday")

    }

    @Test
    fun getDateTime() {
        val today = getDayWeather()

        assertThat(today.getDateTime(1646492400)).isEqualTo(  DayOfWeek.SATURDAY)

    }

    @Test
    fun getColor() {
        val today = getDayWeather()
        assertThat(today.getColor()).isEqualTo( Color.parseColor("#0051FF"))
    }

    @Test
    fun getHourColor() {
        val today = getDayWeather()
        assertThat(today.getHourColor()).isEqualTo( Color.parseColor("#0051FF"))
    }

    @Test
    fun getHourOfDay() {
        val today = getDayWeather()
        assertThat(today.getHourOfDay()).isEqualTo("15:00")
    }

    fun getDayWeather(): DayWeather {
        return DayWeather(
            main = Main(
                temp = 13.88,
                feelsLike = 11.66,
                tempMin = 11.85,
                tempMax = 13.88,
                pressure = 1011,
                humidity = 13,
            ),
            weather = arrayListOf<Weather>(
                Weather(
                    id = 803,
                    main = "Clouds",
                    description = "ابرهای پارچه پارچه شده",
                    icon = "04n"
                )
            ),
            clouds = Clouds(76),
            wind = Wind(
                speed = 8.73,
                deg = 291
            ),
            sys = Sys(
                country = "String",
                id = 1,
                sunrise = 1,
                sunset = 1,
                type = 1
            ),
            pop = 0.0,
            visibility = 10000,
            dt = 1646492400,
            dtTxt = "2022-03-05 15:00:00",


            )
    }


}