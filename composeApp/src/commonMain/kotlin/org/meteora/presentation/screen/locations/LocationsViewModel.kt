package org.meteora.presentation.screen.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.domain.entity.WeatherInfoShort
import org.meteora.domain.repository.WeatherApiRepository
import org.meteora.domain.repository.WeatherLocalRepository
import kotlin.concurrent.Volatile
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class LocationsViewModel(
    private val weatherApiRepository: WeatherApiRepository,
    weatherLocalRepository: WeatherLocalRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    val locationsFlow: StateFlow<List<LocationInfo>> = weatherLocalRepository.getAllLocationsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5000L
            ),
            initialValue = emptyList()
        )

    @Volatile
    private var _keyWeatherMap: Map<String, WeatherInfo> = emptyMap()

    data class State(
        val locationsState: LocationsState = LocationsState.Empty,
    )

    init {
        viewModelScope.launch {
            locationsFlow.collect { locations ->
                _state.update { it.copy(locationsState = LocationsState.Loading) }
                val weathersShort = ArrayList<WeatherInfoShort>(locations.size)
                val keyWeatherMap = HashMap<String, WeatherInfo>(locations.size)
                val deferreds = locations.map { location ->
                    async {
                        weatherApiRepository.getWeather(
                            lat = location.latitude,
                            lon = location.longitude
                        )
                    }
                }
                deferreds.awaitAll().forEach { result ->
                    result.onSuccess { weather ->
                        val key = Uuid.random().toString()
                        weathersShort.add(weather.toShortInfo(key))
                        keyWeatherMap[key] = weather
                    }.onFailure { ex ->
                        Napier.d(message = "getWeather: ${ex.message}", throwable = ex)
                    }
                }
                _keyWeatherMap = keyWeatherMap
                _state.update {
                    it.copy(locationsState = LocationsState.Content(weathersShort))
                }
            }
        }
    }

    fun getLocationInfoByKey(key: String): LocationInfo? = _keyWeatherMap[key]?.toLocationInfo()
}

sealed class LocationsState {
    object Empty : LocationsState()
    object Loading : LocationsState()
    data class Content(val weathers: List<WeatherInfoShort>) : LocationsState()
    data class Error(val throwable: Throwable) : LocationsState()
}