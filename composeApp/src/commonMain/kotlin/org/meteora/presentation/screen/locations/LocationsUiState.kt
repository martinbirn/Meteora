package org.meteora.presentation.screen.locations

import org.meteora.domain.entity.WeatherInfoShort

sealed class LocationsUiState {
    object Empty : LocationsUiState()
    object Loading : LocationsUiState()
    data class Error(val throwable: Throwable) : LocationsUiState()
    data class Content(val weathers: List<WeatherInfoShort>) : LocationsUiState()
}
