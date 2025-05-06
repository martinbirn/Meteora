package org.meteora.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.geo.LatLng
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.meteora.domain.entity.WeatherInfo
import org.meteora.domain.repository.WeatherRepository

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModel(
    val locationTracker: LocationTracker,
    permissionsController: PermissionsController,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val locationStateFlow: StateFlow<LatLng?> = locationTracker.getLocationsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    private val _refreshTrigger = MutableStateFlow(0)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _refreshTrigger.flatMapLatest { _ ->
                locationTracker.getLocationsFlow().take(1)
            }.collect { location ->
                fetchWeather(location.latitude, location.longitude)
            }
        }
    }

    private suspend fun fetchWeather(lat: Double, lon: Double) {
        _state.update { it.copy(weatherState = WeatherState.Loading) }

        weatherRepository.getWeather(lat = lat, lon = lon).onSuccess { weatherInfo ->
            _state.update { state ->
                state.copy(weatherState = WeatherState.Content(weatherInfo))
            }
        }.onFailure { error ->
            _state.update { state ->
                state.copy(
                    weatherState = WeatherState.Error(error),
                    error = error.message
                )
            }
        }
    }

    fun refresh() {
        _refreshTrigger.update { it.inc() }
    }

    suspend fun startTracking() {
        try {
            locationTracker.startTracking()
        } catch (exc: Throwable) {
            _state.update {
                it.copy(
                    textLocation = exc.toString(),
                )
            }
        }
    }

    fun stopTracking() {
        locationTracker.stopTracking()
    }

    data class State(
        val weatherState: WeatherState = WeatherState.Loading,
        val error: String? = null,
        val textLocation: String = "no data",
    )
}

sealed class WeatherState {
    object Loading : WeatherState()
    data class Content(val weatherInfo: WeatherInfo) : WeatherState()
    data class Error(val throwable: Throwable) : WeatherState()
}