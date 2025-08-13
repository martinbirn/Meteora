package org.meteora.presentation.screen.locationsearch.component

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import org.meteora.domain.entity.LocationInfoShort
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentComponent
import org.meteora.presentation.screen.locationsearch.LocationSearchUiState

interface LocationSearchComponent {
    val uiState: StateFlow<LocationSearchUiState>

    val bottomSheetPages: Value<ChildPages<*, BottomSheetContentComponent>>

    val inputText: StateFlow<String>

    fun updateInput(input: String)

    fun clearInput()

    fun navigateToLocationWeather(location: LocationInfoShort)

    fun navigateBack()

    fun dismissBottomSheet()
}