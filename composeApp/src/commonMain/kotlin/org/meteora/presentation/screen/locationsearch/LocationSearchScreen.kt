@file:OptIn(ExperimentalSharedTransitionApi::class)

package org.meteora.presentation.screen.locationsearch

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.LocationInfoShort
import org.meteora.presentation.component.SearchTextField
import org.meteora.presentation.icon.SearchIcon
import org.meteora.presentation.icon.XCircleIcon
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.add
import org.meteora.presentation.resources.cancel
import org.meteora.presentation.resources.search_location_placeholder
import org.meteora.presentation.screen.locationweather.LocationWeatherScreen
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.preview.PreviewSharedLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onLocationClicked: (LocationInfo) -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel: LocationSearchViewModel = koinViewModel()
    val screenState = viewModel.state.collectAsState()

    sharedTransitionScope.LocationSearchContentScreen(
        animatedContentScope = animatedContentScope,
        screenState = screenState,
        inputState = viewModel.inputText.collectAsState(),
        onSearchInputChanged = viewModel::updateInput,
        onClearInputClicked = viewModel::clearInput,
        onLocationClicked = viewModel::showLocation,
        onCancelClicked = navigateBack
    )

    screenState.value.selectedLocation?.let { location ->
        ModalBottomSheet(
            onDismissRequest = viewModel::hideLocation,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Color.Transparent,
            dragHandle = {},
            contentWindowInsets = { WindowInsets.safeDrawing.only(WindowInsetsSides.Top) },
            properties = ModalBottomSheetProperties(false)
        ) {
            Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
            LocationWeatherScreen(
                modifier = Modifier.clip(
                    shape = MeteoraTheme.shape
                ),
                locationInfo = location,
                header = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(resource = Res.string.cancel),
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = viewModel::hideLocation
                                )
                                .padding(vertical = 12.dp),
                            style = LocalTextStyle.current
                        )
                        Text(
                            text = stringResource(resource = Res.string.add),
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = { viewModel.addLocation(locationInfo = location) }
                                )
                                .padding(vertical = 12.dp),
                            fontWeight = FontWeight.Bold,
                            style = LocalTextStyle.current
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SharedTransitionScope.LocationSearchContentScreen(
    animatedContentScope: AnimatedContentScope,
    screenState: State<LocationSearchViewModel.State>,
    inputState: State<String>,
    onSearchInputChanged: (String) -> Unit,
    onClearInputClicked: () -> Unit,
    onLocationClicked: (LocationInfoShort) -> Unit,
    onCancelClicked: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchTextField(
                value = inputState.value,
                onValueChange = onSearchInputChanged,
                modifier = Modifier
                    .weight(1f)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "search-text"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .padding(start = MeteoraTheme.dimen.horizontalPadding),
                placeholder = stringResource(resource = Res.string.search_location_placeholder),
                leadingIcon = {
                    Icon(
                        imageVector = SearchIcon,
                        contentDescription = "Search",
                        tint = MeteoraColor.White50
                    )
                },
                trailingIcon = {
                    if (inputState.value.isNotEmpty()) {
                        Icon(
                            imageVector = XCircleIcon,
                            contentDescription = "Clear",
                            modifier = Modifier.clickable(onClick = onClearInputClicked),
                            tint = MeteoraColor.White50
                        )
                    }
                },
                focusRequester = focusRequester,
            )

            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(resource = Res.string.cancel),
                modifier = Modifier
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "cancel-button"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .padding(end = MeteoraTheme.dimen.horizontalPadding)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onCancelClicked
                    ),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(height = 12.dp))
        when (val searchState = screenState.value.searchState) {
            LocationSearchState.Idle -> {

            }

            LocationSearchState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is LocationSearchState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error :(${searchState.throwable.message})")
                }
            }

            LocationSearchState.NoResult -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No results for \"${inputState.value}\"",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            is LocationSearchState.Content -> {
                SearchResultList(items = searchState.locations, onItemClicked = onLocationClicked)
            }
        }
    }
}

@Composable
private fun SearchResultList(
    items: List<LocationInfoShort>,
    onItemClicked: (LocationInfoShort) -> Unit
) {
    LazyColumn {
        items(
            count = items.size,
            key = { index -> items[index].key }
        ) { index ->
            val item = items[index]
            Text(
                text = item.displayName,
                modifier = Modifier
                    .clickable(onClick = { onItemClicked(item) })
                    .fillMaxWidth()
                    .padding(
                        horizontal = MeteoraTheme.dimen.horizontalPadding,
                        vertical = 10.dp
                    ),
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLocationSearchContentScreen() {
    MeteoraTheme {
        val screenState = remember {
            mutableStateOf(LocationSearchViewModel.State())
        }
        val inputState = remember {
            mutableStateOf("")
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8780A3),
                            Color(0xFFaeaabf)
                        )
                    )
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            PreviewSharedLayout {
                LocationSearchContentScreen(
                    animatedContentScope = it,
                    screenState = screenState,
                    inputState = inputState,
                    onSearchInputChanged = {},
                    onClearInputClicked = {},
                    onLocationClicked = {},
                    onCancelClicked = {}
                )
            }
        }
    }
}
