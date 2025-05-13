package org.meteora.presentation.screen.locationsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.LocationInfoShort
import org.meteora.domain.repository.WeatherApiRepository
import org.meteora.domain.repository.WeatherLocalRepository
import kotlin.concurrent.Volatile
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(FlowPreview::class, ExperimentalUuidApi::class)
class LocationSearchViewModel(
    private val weatherApiRepository: WeatherApiRepository,
    private val weatherLocalRepository: WeatherLocalRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    @Volatile
    private var _keyLocationMap: Map<String, LocationInfo> = emptyMap()

    data class State(
        val searchState: LocationSearchState = LocationSearchState.Idle,
        val selectedLocation: LocationInfo? = null
    )

    init {
        viewModelScope.launch {
            inputText.debounce(500).collectLatest { input ->
                if (input.isBlank()) {
                    _state.update { it.copy(searchState = LocationSearchState.Idle) }
                    return@collectLatest
                }

                weatherApiRepository.searchLocations(query = input).onSuccess { locations ->
                    if (locations.isEmpty()) {
                        _state.update { it.copy(searchState = LocationSearchState.NoResult) }
                        _keyLocationMap = emptyMap()
                    } else {
                        val locationsShort = ArrayList<LocationInfoShort>(locations.size)
                        val keyLocationMap = HashMap<String, LocationInfo>(locations.size)
                        locations.forEach { location ->
                            val key = Uuid.random().toString()
                            locationsShort.add(location.toShortInfo(key))
                            keyLocationMap[key] = location
                        }
                        _keyLocationMap = keyLocationMap
                        _state.update {
                            it.copy(
                                searchState = LocationSearchState.Content(locations = locationsShort)
                            )
                        }
                    }
                }.onFailure { ex ->
                    _state.update { it.copy(searchState = LocationSearchState.Error(ex)) }
                }
            }
        }
    }

    fun updateInput(inputText: String) {
        _inputText.update { inputText }

        if (inputText.isNotBlank()) {
            _state.update { it.copy(searchState = LocationSearchState.Loading) }
        }
    }

    fun clearInput() {
        _state.update { it.copy(searchState = LocationSearchState.Idle) }
        _inputText.update { "" }
    }

    fun showLocation(location: LocationInfoShort) {
        _state.update { it.copy(selectedLocation = _keyLocationMap[location.key]) }
    }

    fun hideLocation() {
        _state.update { it.copy(selectedLocation = null) }
    }

    fun addLocation(locationInfo: LocationInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherLocalRepository.addLocation(locationInfo)
            _state.update { it.copy(selectedLocation = null) }
        }
    }

    fun back() {

    }
}

sealed class LocationSearchState {
    object Idle : LocationSearchState()
    object Loading : LocationSearchState()
    object NoResult : LocationSearchState()
    data class Content(val locations: List<LocationInfoShort>) : LocationSearchState()
    data class Error(val throwable: Throwable) : LocationSearchState()
}