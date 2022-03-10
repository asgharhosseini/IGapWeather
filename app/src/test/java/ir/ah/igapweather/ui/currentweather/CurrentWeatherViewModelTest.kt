package ir.ah.igapweather.ui.currentweather



import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import ir.ah.igapweather.data.model.*
import ir.ah.igapweather.data.repository.currentweather.CurrentWeatherRepository
import ir.ah.igapweather.other.wrapper.Resource
import ir.ah.igapweather.ui.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrentWeatherViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Mock
    lateinit var currentWeatherRepository: CurrentWeatherRepository

    lateinit var vm: CurrentWeatherViewModel

    lateinit var CoroutineDispatcher: TestCoroutineDispatcher


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this);
        CoroutineDispatcher = TestCoroutineDispatcher()
        vm = CurrentWeatherViewModel(CoroutineDispatcher, currentWeatherRepository)
    }


    @Test
    @Throws(Exception::class)
    fun getCurrentWeather() = runBlockingTest {

        val returnedValue: Resource<WeatherResponse> = Resource.Success(getWeatherResponse())

        `when`(
            currentWeatherRepository.getCurrentWeather()
        ).thenReturn(returnedValue)
        vm.getCurrentWeather()

        assertThat(vm.currentWeather.drop(1).first().success).isEqualTo(returnedValue.success)
    }

    @Test
    @Throws(Exception::class)
    fun getCurrentWeatherForSearch() = runBlockingTest {

        val returnedValue: Resource<WeatherResponse> = Resource.Success(getWeatherResponse())

        `when`(
            currentWeatherRepository.getCurrentWeather("tehran")
        ).thenReturn(returnedValue)
        vm.getCurrentWeather()

        assertThat(vm.currentWeather.drop(1).first().success).isEqualTo(returnedValue.success)
    }


    @Test
    @Throws(Exception::class)
    fun getForecastWeather() = runBlockingTest {

        val returnedValue: Resource<ForecastResponse> = Resource.Success(getForecastResponse())

        `when`(
            currentWeatherRepository.getForecastWeather("tehran")
        ).thenReturn(returnedValue)
        vm.getForecastWeather()
        assertThat(vm.currentForecast.drop(1).first().success).isEqualTo(returnedValue.success)
    }


    @Test
    @Throws(Exception::class)
    fun getNextWeather() = runBlockingTest {

        val returnedValue: Resource<ForecastResponse> = Resource.Success(getForecastResponse())

        `when`(
            currentWeatherRepository.getNextWeather("tehran")
        ).thenReturn(returnedValue)
        vm.getNextWeather()

        assertThat(vm.nextWeatherForecast.drop(1).first().success).isEqualTo(returnedValue.success)
    }


    @Test
    @Throws(Exception::class)
    fun getCurrentWeatherLoading() = runBlockingTest {

        val returnedValue: Resource<WeatherResponse> = Resource.Success(getWeatherResponse())


        `when`(
            currentWeatherRepository.getCurrentWeather("tehran")
        ).thenReturn(returnedValue)
        vm.getCurrentWeather()

        assertThat(vm.currentWeather.first().success).isNull()
    }

    @Test
    @Throws(Exception::class)
    fun getForecastWeatherLoading() = runBlockingTest {

        val returnedValue: Resource<ForecastResponse> = Resource.Success(getForecastResponse())


        `when`(
            currentWeatherRepository.getForecastWeather("tehran")
        ).thenReturn(returnedValue)
        vm.getForecastWeather()

        assertThat(vm.currentForecast.first().success).isNull()
    }


    @Test
    @Throws(Exception::class)
    fun getNextWeatherLoading() = runBlockingTest {

        val returnedValue: Resource<ForecastResponse> = Resource.Success(getForecastResponse())


        `when`(
            currentWeatherRepository.getNextWeather("tehran")
        ).thenReturn(returnedValue)
        vm.getNextWeather()

        assertThat(vm.nextWeatherForecast.first().success).isNull()
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