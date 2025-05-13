@file:OptIn(ExperimentalSharedTransitionApi::class)

package org.meteora.presentation.screen.locations

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.meteora.presentation.component.SearchTextField
import org.meteora.presentation.icon.SearchIcon
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.search_location_placeholder
import org.meteora.presentation.resources.weather
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.preview.PreviewSharedLayout

@Composable
fun LocationsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    navigateToLocationSearch: () -> Unit
) {
    val viewModel: LocationsViewModel = koinViewModel()

    sharedTransitionScope.LocationsScreenContent(
        animatedContentScope = animatedContentScope,
        screenState = viewModel.state.collectAsState(),
        onSearchClicked = navigateToLocationSearch
    )
}

@Composable
private fun SharedTransitionScope.LocationsScreenContent(
    animatedContentScope: AnimatedContentScope,
    screenState: State<LocationsViewModel.State>,
    onSearchClicked: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
        Text(
            text = stringResource(resource = Res.string.weather),
            modifier = Modifier.padding(horizontal = MeteoraTheme.dimen.horizontalPadding),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .weight(weight = 1f)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "search-text"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .padding(start = MeteoraTheme.dimen.horizontalPadding),
                readOnly = true,
                onClickWhenReadOnly = onSearchClicked,
                placeholder = stringResource(resource = Res.string.search_location_placeholder),
                leadingIcon = {
                    Icon(
                        imageVector = SearchIcon,
                        contentDescription = "Search",
                        tint = MeteoraColor.White50
                    )
                },
            )

            // empty shared element
            Spacer(
                modifier = Modifier
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "cancel-button"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .padding(end = MeteoraTheme.dimen.horizontalPadding)
                    .width(0.dp)
            )
        }
        Spacer(modifier = Modifier.height(height = 12.dp))
    }
}

@Preview
@Composable
private fun PreviewLocationsScreenContent() {
    MeteoraTheme {
        val screenState = remember {
            mutableStateOf(LocationsViewModel.State())
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
                LocationsScreenContent(
                    animatedContentScope = it,
                    screenState = screenState,
                    onSearchClicked = {}
                )
            }
        }
    }
}