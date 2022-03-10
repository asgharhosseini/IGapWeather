package ir.ah.igapweather.ui.splash

import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.ah.igapweather.R
import ir.ah.igapweather.base.BaseFragment
import ir.ah.igapweather.databinding.FragmentSplashBinding
import ir.ah.igapweather.other.viewBinding
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel>(
    R.layout.fragment_splash, SplashViewModel::class
) {
    private val binding by viewBinding(FragmentSplashBinding::bind)


    override fun observeData() {
        super.observeData()
        lifecycleScope.launchWhenStarted {
            delay(2000)

            //findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToCurrentWeatherFragment())


        }
    }


    override fun setUpViews() {
        super.setUpViews()
    }
}