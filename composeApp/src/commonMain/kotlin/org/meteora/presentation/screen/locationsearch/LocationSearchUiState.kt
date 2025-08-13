package org.meteora.presentation.screen.locationsearch

import org.meteora.domain.entity.LocationInfoShort

sealed class LocationSearchUiState {
    object Idle : LocationSearchUiState()
    object Loading : LocationSearchUiState()
    object NoResult : LocationSearchUiState()
    data class Content(val locations: List<LocationInfoShort>) : LocationSearchUiState()
    data class Error(val throwable: Throwable) : LocationSearchUiState()
}
