package ir.ah.igapweather.ui.currentweather

import ir.ah.igapweather.other.wrapper.FailureInterface

sealed class SearchWeatherEvent{
    object SearchQueryIsEmpty : SearchWeatherEvent()
    data class ShowError(val failure: FailureInterface?) : SearchWeatherEvent()
}
