package org.meteora.presentation.decompose

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.LocationInfoShort
import org.meteora.domain.repository.WeatherApiRepository
import org.meteora.util.coroutineScope

interface LocationSearchComponent {
    val uiState: StateFlow<LocationSearchUiState>

    val inputText: StateFlow<String>

    fun updateInput(input: String)

    fun clearInput()

    fun navigateToLocationWeather(location: LocationInfoShort)

    fun navigateBack()
}

class DefaultLocationSearchComponent(
    componentContext: ComponentContext,
    private val weatherApiRepository: WeatherApiRepository,
    private val onNavigateToLocationWeather: (LocationInfo) -> Unit,
    private val onNavigateBack: () -> Unit,
) : LocationSearchComponent, ComponentContext by componentContext {

    private val coroutineScope = coroutineScope()

    private val _uiState = MutableStateFlow<LocationSearchUiState>(LocationSearchUiState.Idle)
    override val uiState: StateFlow<LocationSearchUiState> = _uiState

    private val _input = MutableStateFlow("")
    override val inputText: StateFlow<String> = _input

    // Temporary map for id -> full LocationInfo mapping
    private var keyLocationMap: Map<String, LocationInfo> = emptyMap()

    init {
        coroutineScope.launch {
            _input
                .debounce(500)
                .collectLatest { text ->
                    if (text.isBlank()) {
                        _uiState.value = LocationSearchUiState.Idle
                        return@collectLatest
                    }

                    _uiState.value = LocationSearchUiState.Loading
                    weatherApiRepository.searchLocations(query = text)
                        .onSuccess { locations ->
                            if (locations.isEmpty()) {
                                keyLocationMap = emptyMap()
                                _uiState.value = LocationSearchUiState.NoResult
                            } else {
                                val map = mutableMapOf<String, LocationInfo>()
                                val shortList = locations.map { loc ->
                                    map[loc.id] = loc
                                    loc.toShortInfo()
                                }
                                keyLocationMap = map
                                _uiState.value = LocationSearchUiState.Content(shortList)
                            }
                        }
                        .onFailure { ex ->
                            _uiState.value = LocationSearchUiState.Error(ex)
                        }
                }
        }
    }

    override fun updateInput(input: String) {
        _input.value = input
    }

    override fun clearInput() {
        _input.value = ""
        _uiState.value = LocationSearchUiState.Idle
    }

    override fun navigateToLocationWeather(location: LocationInfoShort) {
        keyLocationMap[location.id]?.let { onNavigateToLocationWeather(it) }
    }

    override fun navigateBack() {
        onNavigateBack()
    }
}

sealed class LocationSearchUiState {
    object Idle : LocationSearchUiState()
    object Loading : LocationSearchUiState()
    object NoResult : LocationSearchUiState()
    data class Content(val locations: List<LocationInfoShort>) : LocationSearchUiState()
    data class Error(val throwable: Throwable) : LocationSearchUiState()
}
