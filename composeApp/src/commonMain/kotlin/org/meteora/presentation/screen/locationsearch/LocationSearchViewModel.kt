package org.meteora.presentation.screen.locationsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.meteora.domain.repository.WeatherRepository

@OptIn(FlowPreview::class)
class LocationSearchViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    data class State(
        val searchState: LocationSearchState = LocationSearchState.Idle,
    )

    init {
        viewModelScope.launch {
            inputText.debounce(500).collectLatest { input ->
                if (input.isBlank()) {
                    _state.update { it.copy(searchState = LocationSearchState.Idle) }
                    return@collectLatest
                }

                weatherRepository.searchLocations(query = input).onSuccess { locations ->
                    if (locations.isEmpty()) {
                        _state.update { it.copy(searchState = LocationSearchState.NoResult) }
                    } else {
                        _state.update {
                            it.copy(
                                searchState = LocationSearchState.Content(
                                    locations = locations.distinct()
                                )
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

    fun selectLocation(location: String) {

    }

    fun back() {

    }
}

sealed class LocationSearchState {
    object Idle : LocationSearchState()
    object Loading : LocationSearchState()
    object NoResult : LocationSearchState()
    data class Content(val locations: List<String>) : LocationSearchState()
    data class Error(val throwable: Throwable) : LocationSearchState()
}