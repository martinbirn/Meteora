package org.meteora.presentation.screen.locationweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.geo.LatLng
import dev.icerock.moko.geo.LocationTracker
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.meteora.domain.entity.WeatherInfo
import org.meteora.domain.repository.WeatherRepository

@OptIn(ExperimentalCoroutinesApi::class)
class LocationWeatherViewModel(
    val locationTracker: LocationTracker,
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

    data class State(
        val weatherState: LocationWeatherState = LocationWeatherState.Loading,
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _refreshTrigger.drop(1).flatMapLatest {
                Napier.d("_refreshTrigger triggered: $it")
                locationTracker.getLocationsFlow().take(1)
            }.collect { location ->
                Napier.d("_refreshTrigger collected: $location")
                fetchWeather(location.latitude, location.longitude)
            }
        }
    }

    private suspend fun fetchWeather(lat: Double, lon: Double) {
        _state.update { it.copy(weatherState = LocationWeatherState.Loading) }

        weatherRepository.getWeather(lat = lat, lon = lon).onSuccess { weatherInfo ->
            _state.update { state ->
                state.copy(weatherState = LocationWeatherState.Content(weatherInfo))
            }
        }.onFailure { ex ->
            Napier.e(message = ex.message.orEmpty(), throwable = ex)
            _state.update {
                it.copy(weatherState = LocationWeatherState.Error(ex))
            }
        }
    }

    fun refresh() {
        _refreshTrigger.update { it.inc() }
    }

    suspend fun startTracking() {
        try {
            locationTracker.startTracking()
        } catch (ex: Throwable) {
            Napier.e(message = ex.message.orEmpty(), throwable = ex)
            _state.update {
                it.copy(weatherState = LocationWeatherState.Error(ex))
            }
        }
    }

    fun stopTracking() {
        locationTracker.stopTracking()
    }
}

sealed class LocationWeatherState {
    object Loading : LocationWeatherState()
    data class Content(val weatherInfo: WeatherInfo) : LocationWeatherState()
    data class Error(val throwable: Throwable) : LocationWeatherState()
}