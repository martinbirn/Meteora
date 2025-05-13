package org.meteora.presentation.screen.locationweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.geo.LocationTracker
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.domain.repository.WeatherApiRepository

@OptIn(ExperimentalCoroutinesApi::class)
class LocationWeatherViewModel(
    private val locationInfo: LocationInfo?,
    private val weatherApiRepository: WeatherApiRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        value = State(
            weatherState = if (locationInfo != null) {
                LocationWeatherState.Init(locationInfo = locationInfo)
            } else {
                LocationWeatherState.Loading
            }
        )
    )
    val state = _state.asStateFlow()

    private var locationTracker: LocationTracker? = null
    private val _refreshTrigger = MutableStateFlow(0)

    data class State(
        val weatherState: LocationWeatherState = LocationWeatherState.Loading,
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _refreshTrigger.drop(1).flatMapLatest {
                Napier.d("_refreshTrigger triggered: $it")
                when {
                    // show weather for parameter location
                    locationInfo != null -> flowOf(locationInfo.latLng)
                    // show weather for user's current location
                    locationTracker != null -> locationTracker!!.getLocationsFlow().take(1)
                    // don't have source
                    else -> emptyFlow()
                }
            }.collect { location ->
                Napier.d("_refreshTrigger collected: $location")
                fetchWeather(location.latitude, location.longitude)
            }
        }
    }

    private suspend fun fetchWeather(lat: Double, lon: Double) {
        _state.update { it.copy(weatherState = LocationWeatherState.Loading) }

        weatherApiRepository.getWeather(lat = lat, lon = lon).onSuccess { weatherInfo ->
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

    suspend fun startTracking(locationTracker: LocationTracker) {
        try {
            this.locationTracker = locationTracker.also { it.startTracking() }
        } catch (ex: Throwable) {
            Napier.e(message = ex.message.orEmpty(), throwable = ex)
            _state.update {
                it.copy(weatherState = LocationWeatherState.Error(ex))
            }
        }
    }

    fun stopTracking() {
        locationTracker?.stopTracking()
        locationTracker = null
    }
}

sealed class LocationWeatherState {
    data class Init(val locationInfo: LocationInfo) : LocationWeatherState()
    object Loading : LocationWeatherState()
    data class Content(val weatherInfo: WeatherInfo) : LocationWeatherState()
    data class Error(val throwable: Throwable) : LocationWeatherState()
}