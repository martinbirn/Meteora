package org.meteora.presentation.decompose

import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.geo.LocationTracker
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
import org.meteora.util.coroutineScope

interface LocationWeatherComponent {
    val weatherState: StateFlow<LocationWeatherUiState>

    val initialLocation: LocationInfo?

    // Trigger a manual refresh of weather data
    fun refresh()

    // Start tracking user location (if initial location not provided)
    suspend fun startTracking(locationTracker: LocationTracker)

    // Stop location tracking when leaving the screen
    fun stopTracking()
}

class DefaultLocationWeatherComponent(
    componentContext: ComponentContext,
    override val initialLocation: LocationInfo?,
    private val weatherApiRepository: WeatherApiRepository,
) : LocationWeatherComponent, ComponentContext by componentContext {

    private val coroutineScope = coroutineScope()

    private val _weatherState = MutableStateFlow(
        initialLocation?.let { LocationWeatherUiState.Init(it) }
            ?: LocationWeatherUiState.Loading
    )
    override val weatherState: StateFlow<LocationWeatherUiState> = _weatherState

    // Internal trigger for refresh events
    private val _refreshTrigger = MutableStateFlow<Int>(0)

    private var locationTracker: LocationTracker? = null

    init {
        coroutineScope.launch {
            _refreshTrigger
                .drop(1)
                .flatMapLatest {
                    Napier.d("_refreshTrigger triggered: $it")
                    when {
                        // Use initial location if provided
                        initialLocation != null -> flowOf(initialLocation.latLng)
                        // Otherwise use locationTracker if started
                        locationTracker != null -> locationTracker!!.getLocationsFlow().take(1)
                        // No source available
                        else -> emptyFlow()
                    }
                }
                .collect { (latitude, longitude) ->
                    Napier.d("_refreshTrigger collected: $latitude, $longitude")
                    fetchWeather(lat = latitude, lon = longitude)
                }
        }
    }

    override fun refresh() {
        _refreshTrigger.update { it + 1 }
    }

    override suspend fun startTracking(locationTracker: LocationTracker) {
        try {
            this.locationTracker = locationTracker.also { it.startTracking() }
        } catch (ex: Throwable) {
            _weatherState.update { LocationWeatherUiState.Error(ex) }
        }
    }

    override fun stopTracking() {
        locationTracker?.stopTracking()
        locationTracker = null
    }

    private suspend fun fetchWeather(lat: Double, lon: Double) {
        _weatherState.update { LocationWeatherUiState.Loading }

        weatherApiRepository.getWeather(lat = lat, lon = lon)
            .onSuccess { weatherInfo ->
                _weatherState.update { LocationWeatherUiState.Content(weatherInfo) }
            }
            .onFailure { ex ->
                _weatherState.update { LocationWeatherUiState.Error(ex) }
            }
    }
}

sealed class LocationWeatherUiState {
    data class Init(val locationInfo: LocationInfo) : LocationWeatherUiState()
    object Loading : LocationWeatherUiState()
    data class Content(val weatherInfo: WeatherInfo) : LocationWeatherUiState()
    data class Error(val throwable: Throwable) : LocationWeatherUiState()
}
