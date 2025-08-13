package org.meteora.presentation.screen.locationsearch.component

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.meteora.domain.entity.LocationInfoShort
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentComponent
import org.meteora.presentation.screen.locationsearch.LocationSearchUiState
import org.meteora.presentation.screen.locationsearch.component.DefaultLocationSearchComponent.SearchBottomSheetConfig

class PreviewLocationSearchComponent() : LocationSearchComponent {

    override val uiState: StateFlow<LocationSearchUiState> = MutableStateFlow(
        LocationSearchUiState.Content(
            locations = listOf(
                LocationInfoShort(
                    id = "1",
                    displayName = "New York, NY, USA",
                    latitude = 40.7128,
                    longitude = -74.0060
                ),
                LocationInfoShort(
                    id = "2",
                    displayName = "London, England, UK",
                    latitude = 51.5074,
                    longitude = -0.1278
                ),
                LocationInfoShort(
                    id = "3",
                    displayName = "Tokyo, Japan",
                    latitude = 35.6762,
                    longitude = 139.6503
                )
            )
        )
    )

    override val bottomSheetPages: Value<ChildPages<SearchBottomSheetConfig, BottomSheetContentComponent>> =
        MutableValue(ChildPages(listOf(), 0))

    override val inputText: StateFlow<String> = MutableStateFlow("New")

    override fun updateInput(input: String) {}

    override fun clearInput() {}

    override fun navigateToLocationWeather(location: LocationInfoShort) {}

    override fun navigateBack() {}

    override fun dismissBottomSheet() {}
}