package org.meteora.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.meteora.presentation.component.tokens.TopAppBarTokens

@Composable
fun CollapsibleTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    searchField: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    expandedHeight: Dp = TopAppBarTokens.ContainerHeight
) {
    val headerTranslation = (expandedHeight / 2)
    val scrollState = scrollBehavior.state
    val appBarExpanded by remember {
        derivedStateOf { scrollState.collapsedFraction < 0.9f }
    }

    Column(modifier = modifier) {
        TopAppBar(
            title = {
                AnimatedVisibility(
                    visible = !appBarExpanded,
                    enter = fadeIn(animationSpec = tween()),
                    exit = fadeOut(animationSpec = tween())
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            modifier = modifier,
            expandedHeight = expandedHeight,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
            )
        )

        TopAppBar(
            title = {
                AnimatedVisibility(
                    visible = appBarExpanded,
                    enter = fadeIn(animationSpec = tween()),
                    exit = fadeOut(animationSpec = tween())
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.graphicsLayer {
                            translationY = -scrollState.collapsedFraction * headerTranslation.toPx()
                        }
                    )
                }
            },
            expandedHeight = expandedHeight,
            windowInsets = WindowInsets(0.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
            ),
            scrollBehavior = scrollBehavior
        )

        searchField()
    }
}
