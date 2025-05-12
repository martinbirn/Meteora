package org.meteora.presentation.screen.locationsearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.meteora.presentation.component.SearchTextField
import org.meteora.presentation.icon.MicIcon
import org.meteora.presentation.icon.SearchIcon
import org.meteora.presentation.icon.XCircleIcon
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.cancel
import org.meteora.presentation.resources.search_location_placeholder
import org.meteora.presentation.resources.weather
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme

@Composable
fun LocationSearchScreen() {
    val viewModel: LocationSearchViewModel = koinViewModel()

    LocationSearchContentScreen(
        screenState = viewModel.state.collectAsState(),
        inputState = viewModel.inputText.collectAsState(),
        onSearchInputChanged = viewModel::updateInput,
        onClearInputClicked = viewModel::clearInput,
        onLocationClicked = viewModel::selectLocation,
        onCancelClicked = viewModel::back
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationSearchContentScreen(
    screenState: State<LocationSearchViewModel.State>,
    inputState: State<String>,
    onSearchInputChanged: (String) -> Unit,
    onClearInputClicked: () -> Unit,
    onLocationClicked: (String) -> Unit,
    onCancelClicked: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
        Text(
            text = stringResource(resource = Res.string.weather),
            modifier = Modifier.padding(horizontal = MeteoraTheme.dimen.horizontalPadding),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(height = 4.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            SearchTextField(
                value = inputState.value,
                onValueChange = onSearchInputChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MeteoraTheme.dimen.horizontalPadding),
                placeholder = stringResource(resource = Res.string.search_location_placeholder),
                cancelButton = {
                    Text(
                        text = stringResource(resource = Res.string.cancel),
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = onCancelClicked
                            ),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = SearchIcon,
                        contentDescription = "Search",
                        tint = MeteoraColor.White50
                    )
                },
                trailingIcon = {
                    if (inputState.value.isEmpty()) {
                        Icon(
                            imageVector = MicIcon,
                            contentDescription = "Microphone",
                            modifier = Modifier,// TODO: Implement Mic using
                            tint = MeteoraColor.White50
                        )
                    } else {
                        Icon(
                            imageVector = XCircleIcon,
                            contentDescription = "Clear",
                            modifier = Modifier.clickable(onClick = onClearInputClicked),
                            tint = MeteoraColor.White50
                        )
                    }
                }
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
    items: List<String>,
    onItemClicked: (String) -> Unit
) {
    LazyColumn {
        items(items.size) { index ->
            val item = items[index]
            Text(
                text = item,
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

}