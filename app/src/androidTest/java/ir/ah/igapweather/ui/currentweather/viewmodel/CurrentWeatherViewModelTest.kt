package ir.ah.igapweather.ui.currentweather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.ah.igapweather.data.model.*
import ir.ah.igapweather.data.repository.currentweather.CurrentWeatherRepository
import ir.ah.igapweather.other.wrapper.Resource
import ir.ah.igapweather.ui.currentweather.CurrentWeatherViewModel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class CurrentWeatherViewModelTest{
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var currentWeatherRepository :CurrentWeatherRepository

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var mainCoroutineDispatcher: CoroutineDispatcher

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private val weatherJsonAdapter get() = moshi.adapter(WeatherResponse::class.java)
    private val forecastJsonAdapter get() = moshi.adapter(ForecastResponse::class.java)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        currentWeatherViewModel = CurrentWeatherViewModel(mainCoroutineDispatcher, currentWeatherRepository)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        mainCoroutineDispatcher.cancel()
    }

    private fun getWeatherEnqueueSuccessResponse() = runBlocking {
        mockWebServer.start(port = 57594)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(weatherJsonAdapter.toJson(getWeatherResponse()))
        )
    }
    private fun getForecastEnqueueSuccessResponse() = runBlocking {
        mockWebServer.start(port = 57594)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(forecastJsonAdapter.toJson(getForecastResponse()))
        )
    }


    @Test
    fun getWeatherTest() = runBlocking {
        getWeatherEnqueueSuccessResponse()
        currentWeatherViewModel.getCurrentWeather()
        val resource = currentWeatherViewModel.currentWeather.take(2).toList()
        assertThat(resource[0] is Resource.Loading).isTrue()
        assertThat(resource[1].success).isEqualTo(getWeatherResponse())
    }
    @Test
    fun getNextWeatherTest() = runBlocking {
        getWeatherEnqueueSuccessResponse()
        currentWeatherViewModel.getNextWeather()
        val resource = currentWeatherViewModel.nextWeatherForecast.take(2).toList()
        assertThat(resource[0] is Resource.Loading).isTrue()
        assertThat(resource[1].success).isEqualTo(getWeatherResponse())
    }

    @Test
    fun getForecastTest() = runBlocking {
        getForecastEnqueueSuccessResponse()
        currentWeatherViewModel.getForecastWeather()
        val resource = currentWeatherViewModel.currentForecast.take(2).toList()
        assertThat(resource[0] is Resource.Loading).isTrue()
        assertThat(resource[1].success).isEqualTo(getForecastResponse())
    }


    fun getWeatherResponse(): WeatherResponse {
        return WeatherResponse(
            base = "stations",
            clouds = Clouds(100),
            cod = 200,
            coord = Coord(lon = 54.42, lat = 35.69), dt = 1646635721, id = 47737,
            main = Main(
                temp = 13.88,
                feelsLike = 11.66,
                tempMin = 11.85,
                tempMax = 13.88,
                pressure = 1011,
                humidity = 13,
            ),
            name = "تهران",
            sys = Sys(
                country = "String",
                id = 1,
                sunrise = 1,
                sunset = 1,
                type = 1
            ),
            timezone = 12600,
            visibility = 10000,
            weather = arrayListOf<Weather>(
                Weather(
                    id = 803,
                    main = "Clouds",
                    description = "ابرهای پارچه پارچه شده",
                    icon = "04n"
                )
            ),
            wind = Wind(speed = 8.73, deg = 291)
        )

    }

    fun getForecastResponse(): ForecastResponse {
        return ForecastResponse(

            city = City(
                coord = Coord(
                    lat = 4.2,
                    lon = 4.2
                ),
                country = "String?",
                id = 1,
                name = "String",
                population = 1,
                sunrise = 1,
                sunset = 1,
                timezone = 1
            ),
            cnt = 4,
            cod = "tring?",
            dayWeather = arrayListOf<DayWeather>(getDayWeather()),
            message = 2.0
        )
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