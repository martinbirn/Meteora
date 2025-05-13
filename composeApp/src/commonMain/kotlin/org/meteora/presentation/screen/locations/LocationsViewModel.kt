package org.meteora.presentation.screen.locations

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.meteora.domain.entity.WeatherInfoShort
import org.meteora.domain.repository.WeatherApiRepository

class LocationsViewModel(
    private val weatherApiRepository: WeatherApiRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    data class State(
        val locationsState: LocationsState = LocationsState.Loading,
    )

    fun onSearchClicked() {

    }
}

sealed class LocationsState {
    object Loading : LocationsState()
    data class Content(val weatherInfo: List<WeatherInfoShort>) : LocationsState()
    data class Error(val throwable: Throwable) : LocationsState()
}