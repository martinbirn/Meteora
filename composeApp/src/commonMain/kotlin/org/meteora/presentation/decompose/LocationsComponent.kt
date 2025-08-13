package org.meteora.presentation.decompose

import com.arkivanov.decompose.ComponentContext
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.domain.entity.WeatherInfoShort
import org.meteora.domain.repository.WeatherApiRepository
import org.meteora.domain.repository.WeatherLocalRepository
import org.meteora.util.coroutineScope
import kotlin.uuid.Uuid

interface LocationsComponent {
    val uiState: StateFlow<LocationsUiState>

    fun getLocationInfoByKey(key: String): LocationInfo?

    fun navigateToLocationSearch()
    fun navigateToLocationWeather(locationInfo: LocationInfo)
}

class DefaultLocationsComponent(
    componentContext: ComponentContext,
    private val weatherApiRepository: WeatherApiRepository,
    private val weatherLocalRepository: WeatherLocalRepository,
    private val onNavigateToLocationSearch: () -> Unit,
    private val onNavigateToLocationWeather: (LocationInfo) -> Unit,
) : LocationsComponent, ComponentContext by componentContext {

    private val coroutineScope = coroutineScope()

    private val _uiState = MutableStateFlow<LocationsUiState>(LocationsUiState.Empty)
    override val uiState: StateFlow<LocationsUiState> = _uiState

    // Temporary map for id -> full WeatherInfo mapping
    private var keyWeatherMap: Map<String, WeatherInfo> = emptyMap()

    init {
        coroutineScope.launch {
            weatherLocalRepository.getAllLocationsFlow()
                .collect { locations ->
                    _uiState.value = LocationsUiState.Loading

                    val weathersShort = mutableListOf<WeatherInfoShort>()
                    val map = mutableMapOf<String, WeatherInfo>()

                    val deferreds = locations.map { loc ->
                        async { weatherApiRepository.getWeather(loc.latitude, loc.longitude) }
                    }
                    deferreds.awaitAll().forEach { result ->
                        result.onSuccess { weather ->
                            val key = Uuid.random().toString()
                            weathersShort += weather.toShortInfo(key)
                            map[key] = weather
                        }
                        result.onFailure { ex ->
                            Napier.d(message = "getWeather: ${ex.message}", throwable = ex)
                            _uiState.value = LocationsUiState.Error(ex)
                        }
                    }

                    keyWeatherMap = map
                    _uiState.value = LocationsUiState.Content(weathersShort)
                }
        }
    }

    override fun getLocationInfoByKey(key: String): LocationInfo? =
        keyWeatherMap[key]?.locationInfo

    override fun navigateToLocationSearch() {
        onNavigateToLocationSearch()
    }

    override fun navigateToLocationWeather(locationInfo: LocationInfo) {
        onNavigateToLocationWeather(locationInfo)
    }
}

sealed class LocationsUiState {
    object Empty : LocationsUiState()
    object Loading : LocationsUiState()
    data class Error(val throwable: Throwable) : LocationsUiState()
    data class Content(val weathers: List<WeatherInfoShort>) : LocationsUiState()
}
