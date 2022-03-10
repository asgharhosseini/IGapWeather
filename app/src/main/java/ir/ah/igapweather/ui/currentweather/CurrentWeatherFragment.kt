package ir.ah.igapweather.ui.currentweather
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import ir.ah.igapweather.R
import ir.ah.igapweather.base.BaseFragment
import ir.ah.igapweather.data.model.WeatherResponse
import ir.ah.igapweather.ui.currentweather.adapter.CurrentForecastAdapter
import ir.ah.igapweather.databinding.FragmentCurrentWeatherBinding
import ir.ah.igapweather.di.EndPoint
import ir.ah.igapweather.other.util.ForecastMapper
import ir.ah.igapweather.other.viewBinding
import ir.ah.igapweather.other.wrapper.Resource
import ir.ah.igapweather.ui.currentweather.adapter.NextWeatherForecastAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class CurrentWeatherFragment : BaseFragment<CurrentWeatherViewModel>(
    R.layout.fragment_current_weather,
    CurrentWeatherViewModel::class
) {
    private val binding by viewBinding(FragmentCurrentWeatherBinding::bind)

    @Inject
    lateinit var currentForecastAdapter: CurrentForecastAdapter

    @Inject
    lateinit var nextWeatherForecastAdapter: NextWeatherForecastAdapter

    @Inject
    lateinit var forecastMapper: ForecastMapper

    @Inject
    lateinit var glide: RequestManager

    override fun setUpViews() {
        super.setUpViews()
        setUpAdapter()

    }

    private fun setUpAdapter() {
        binding.currentForecastRecyclerView.apply {
            adapter = currentForecastAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        binding.nextWeatherForecastRecyclerView.apply {
            adapter = nextWeatherForecastAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun observeData() {
        super.observeData()
        subscribeToObserve()
    }

    private fun subscribeToObserve() {
        vm.getCurrentWeather()
        vm.getForecastWeather()
        vm.getNextWeather()

        lifecycleScope.launchWhenStarted {
            vm.currentWeather.collectLatest { event ->
                handleResource(event) { vm.getCurrentWeather() }
                when (event) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        initUIItem(event.success)
                    }
                    is Resource.Failure -> {
                    }
                }

            }
        }

        lifecycleScope.launchWhenStarted {
            vm.currentForecast.collectLatest { event ->
                handleResource(event) { vm.getForecastWeather() }
                when (event) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        currentForecastAdapter.submitList(event.success.dayWeather)
                    }
                    is Resource.Failure -> {

                    }
                }

            }
        }
        lifecycleScope.launchWhenStarted {
            vm.nextWeatherForecast.collect { event ->
                handleResource(event) { vm.getNextWeather() }
                when (event) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        Log.e("sdasdasd", event.success.dayWeather?.size.toString())
                        event.success.dayWeather?.let {
                            Log.e("sdasdasd", forecastMapper.mapFrom(it).size.toString())
                            nextWeatherForecastAdapter.submitList(forecastMapper.mapFrom(it))

                        }



                    }
                    is Resource.Failure -> {

                    }
                }

            }
        }
    }

    private fun initUIItem(weather: WeatherResponse) {
        glide.load("${EndPoint.API_IMAGE_URL + weather.weather.get(0).icon}.png")
            .into(binding.weatherImageView)
        binding.weatherTempView.text = weather.main.temp.toString()
        binding.weatherView.text = weather.weather.get(0).main
        binding.weatherCardView.setCardBackgroundColor(weather.getColor())
        binding.weatherCardView.setOnClickListener {
          //  findNavController().navigate(R.id.action_currentWeatherFragment_to_nextWeatherFragment)
        }
    }
}