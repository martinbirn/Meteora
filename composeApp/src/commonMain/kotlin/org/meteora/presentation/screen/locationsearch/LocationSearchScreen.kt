package org.meteora.presentation.screen.locationsearch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.domain.entity.LocationInfoShort
import org.meteora.presentation.component.SearchTextField
import org.meteora.presentation.decompose.bottomsheet.MeteoraBottomSheetContent
import org.meteora.presentation.decompose.bottomsheet.pages.ChildPagesModalBottomSheet
import org.meteora.presentation.icon.MeteoraIcons
import org.meteora.presentation.icon.Search
import org.meteora.presentation.icon.XCircle
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.cancel
import org.meteora.presentation.resources.search_location_placeholder
import org.meteora.presentation.screen.locationsearch.component.LocationSearchComponent
import org.meteora.presentation.screen.locationsearch.component.PreviewLocationSearchComponent
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme

@Composable
fun LocationSearchScreen(
    component: LocationSearchComponent,
    modifier: Modifier = Modifier,
) {
    Box {
        ScreenContent(
            component = component,
            modifier = modifier
        )

        ChildPagesModalBottomSheet(
            sheetContentPagesState = component.bottomSheetPages,
            onDismiss = component::dismissBottomSheet,
            containerColor = Color.Transparent,
            dragHandle = {},
            contentWindowInsets = { WindowInsets.safeDrawing.only(WindowInsetsSides.Top) },
        ) { component ->
            MeteoraBottomSheetContent(component)
        }
    }
}

@Composable
private fun ScreenContent(
    component: LocationSearchComponent,
    modifier: Modifier = Modifier,
) {
    val screenState = component.uiState.collectAsState()
    val inputState = component.inputText.collectAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchTextField(
                value = inputState.value,
                onValueChange = component::updateInput,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = MeteoraTheme.dimen.horizontalPadding),
                placeholder = stringResource(resource = Res.string.search_location_placeholder),
                leadingIcon = {
                    Icon(
                        imageVector = MeteoraIcons.Search,
                        contentDescription = "Search",
                        tint = MeteoraColor.White50
                    )
                },
                trailingIcon = {
                    if (inputState.value.isNotEmpty()) {
                        Icon(
                            imageVector = MeteoraIcons.XCircle,
                            contentDescription = "Clear",
                            modifier = Modifier.clickable(onClick = component::clearInput),
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
                    .padding(end = MeteoraTheme.dimen.horizontalPadding)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = component::navigateBack
                    ),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(height = 12.dp))
        when (val searchState = screenState.value) {
            is LocationSearchUiState.Idle -> {

            }

            is LocationSearchUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is LocationSearchUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error :(${searchState.throwable.message})")
                }
            }

            LocationSearchUiState.NoResult -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No results for \"${inputState.value}\"",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            is LocationSearchUiState.Content -> {
                SearchResultList(
                    items = searchState.locations,
                    onItemClicked = component::navigateToLocationWeather
                )
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
            key = { index -> items[index].id }
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
private fun LocationSearchScreenPreview() {
    MeteoraTheme {
        LocationSearchScreen(PreviewLocationSearchComponent())
    }
}
