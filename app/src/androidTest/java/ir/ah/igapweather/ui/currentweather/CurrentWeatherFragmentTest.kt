package ir.ah.igapweather.ui.currentweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.ah.igapweather.*
import ir.ah.igapweather.data.model.*
import ir.ah.igapweather.other.wrapper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject
import ir.ah.igapweather.R
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class CurrentWeatherFragmentTest{
    @get:Rule()
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var fakeNetworkConnectionInterceptor: FakeNetworkConnectionInterceptor

    @Inject
    lateinit var mainCoroutineDispatcher: CoroutineDispatcher

    private val ResponseJsonAdapter get() = moshi.adapter(WeatherResponse::class.java)


    @Before
    fun setUp() {
        hiltAndroidRule.inject()

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        mainCoroutineDispatcher.cancel()
    }

    private fun enqueueSuccessResponse()= runBlocking {
        mockWebServer.start(port = 57594)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(ResponseJsonAdapter.toJson(getWeatherResponse())))
    }



    suspend fun getWeatherResponse(local: String): Resource<WeatherResponse> =
        Resource.Success(getWeatherResponse())

    @Test
    fun testWeatherCardViewEqualResponseMain() {
        enqueueSuccessResponse()
        launchFragmentInHiltContainer<CurrentWeatherFragment>()
        onView(withText(getWeatherResponse().weather[0].main)).check(matches(isDisplayed()))


    }

    @Test
    fun testWeatherCardViewIsDisplayed() {
        enqueueSuccessResponse()
        launchFragmentInHiltContainer<CurrentWeatherFragment>()
        onView(withId(R.id.weatherCardView)).check(matches(isDisplayed()))

    }
    @Test
    fun testWeatherTempViewEqualResponse() {
        enqueueSuccessResponse()
        launchFragmentInHiltContainer<CurrentWeatherFragment>()
        onView(withText(getWeatherResponse().main.temp.toString())).check(matches(isDisplayed()))


    }

    @Test
    fun testWeatherTempViewIsDisplayed() {
        enqueueSuccessResponse()
        launchFragmentInHiltContainer<CurrentWeatherFragment>()
        onView(withId(R.id.weatherTempView)).check(matches(isDisplayed()))


    }


    @Test
    fun testWeatherImageViewViewIsDisplayed() {
        enqueueSuccessResponse()
        launchFragmentInHiltContainer<CurrentWeatherFragment>()
        onView(withId(R.id.weatherImageView)).check(matches(isDisplayed()))


    }

    @Test
    fun navigateBetweenCurrentWeatherFragmentAndNextWeatherFragmentAfterClick () {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<CurrentWeatherFragment>() {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.weatherCardView)).perform(click())

        verify(navController).navigate(
            CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToNextWeatherFragment()
        )
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