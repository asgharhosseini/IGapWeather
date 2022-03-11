package ir.ah.igapweather.base

import androidx.lifecycle.*
import kotlinx.coroutines.*

/**
 * @author Asghar hosseini
 *
 *
 * @deprecated
 *This class was created as the parent of all viewModel classes
 */

abstract class BaseViewModel(private val mainCoroutineDispatcher: CoroutineDispatcher) :
    ViewModel() {

    fun doInMain(action: suspend () -> Unit) =
        viewModelScope.launch(mainCoroutineDispatcher) { action.invoke() }
}