package ir.ah.igapweather.ui.splash
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ah.igapweather.base.BaseViewModel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mainCoroutineDispatcher: CoroutineDispatcher
) : BaseViewModel(mainCoroutineDispatcher) {

}