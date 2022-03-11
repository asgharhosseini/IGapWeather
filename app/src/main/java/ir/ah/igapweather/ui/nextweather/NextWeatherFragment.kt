package ir.ah.igapweather.ui.nextweather
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import ir.ah.igapweather.MainActivity
import ir.ah.igapweather.R
import ir.ah.igapweather.base.BaseFragment
import ir.ah.igapweather.data.model.WeatherResponse
import ir.ah.igapweather.databinding.FragmentCurrentWeatherBinding
import ir.ah.igapweather.databinding.FragmentNextWeatherBinding
import ir.ah.igapweather.di.EndPoint
import ir.ah.igapweather.other.util.ForecastMapper
import ir.ah.igapweather.other.viewBinding
import ir.ah.igapweather.other.wrapper.Resource
import ir.ah.igapweather.ui.currentweather.adapter.CurrentForecastAdapter
import ir.ah.igapweather.ui.currentweather.adapter.NextWeatherForecastAdapter
import ir.ah.igapweather.ui.nextweather.adapter.NextWeatherAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class NextWeatherFragment : BaseFragment<NextWeatherViewModel>(
    R.layout.fragment_next_weather, NextWeatherViewModel::class){
    private val binding by viewBinding(FragmentNextWeatherBinding::bind)

    @Inject
    lateinit var nextWeatherAdapter: NextWeatherAdapter



    @Inject
    lateinit var forecastMapper: ForecastMapper



    override fun setUpViews() {
        super.setUpViews()
        setUpAdapter()

    }

    private fun setUpAdapter() {

        binding.nextWeatherForecastRecyclerView.apply {
            adapter = nextWeatherAdapter
            layoutManager =
                GridLayoutManager(requireContext(),3)
        }
    }

    override fun observeData() {
        super.observeData()
        subscribeToObserve()
    }

    private fun subscribeToObserve() {

        vm.getNextWeather()

        lifecycleScope.launchWhenStarted {
            vm.nextWeatherForecast.collect { event ->
                handleResource(event) { vm.getNextWeather() }
                when (event) {
                    is Resource.Loading -> {
                        binding.loadingView.isVisible=true

                    }
                    is Resource.Success -> {
                        Log.e("sdasdasd", event.success.dayWeather?.size.toString())
                        event.success.dayWeather?.let {
                            Log.e("sdasdasd", forecastMapper.mapFrom(it).size.toString())
                            nextWeatherAdapter.submitList(forecastMapper.mapFrom(it))

                        }
                        binding.loadingView.isVisible=false



                    }
                    is Resource.Failure -> {

                    }
                }

            }
        }
    }

}