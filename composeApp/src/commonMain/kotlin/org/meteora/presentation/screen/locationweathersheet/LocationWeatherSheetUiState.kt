package org.meteora.presentation.screen.locationweathersheet

import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentState

data class LocationWeatherSheetUiState(
    override val isDismissAllowed: Boolean,
) : BottomSheetContentState
