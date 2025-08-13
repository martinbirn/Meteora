package org.meteora.presentation.screen.locationweathersheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.presentation.decompose.bottomsheet.ComponentModalBottomSheet
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.add
import org.meteora.presentation.resources.cancel
import org.meteora.presentation.screen.locationweather.LocationWeatherScreen
import org.meteora.presentation.screen.locationweathersheet.component.LocationWeatherSheetComponent
import org.meteora.presentation.screen.locationweathersheet.component.PreviewLocationWeatherSheetComponent
import org.meteora.presentation.theme.MeteoraTheme

/**
 * A modal bottom sheet that displays weather information for a specific location.
 * This Composable acts as a wrapper around [LocationWeatherScreen], presenting it within a [ComponentModalBottomSheet]
 * to facilitate adding the displayed location to the list of saved locations.
 *
 * This sheet allows users to add the displayed location to their saved locations.
 *
 * @param component The [LocationWeatherSheetComponent] that manages the state and logic for this sheet.
 * @param modifier Optional [Modifier] to be applied to the root of this Composable.
 */
@Composable
fun LocationWeatherSheet(
    component: LocationWeatherSheetComponent,
    modifier: Modifier = Modifier,
) {
    LocationWeatherScreen(
        component = component.weatherComponent,
        modifier = Modifier
            .systemBarsPadding()
            .clip(shape = MeteoraTheme.shape),
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
                            onClick = component::navigateBack
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
                            onClick = {
                                component.addLocation(locationInfo = component.locationInfo)
                            }
                        )
                        .padding(vertical = 12.dp),
                    fontWeight = FontWeight.Bold,
                    style = LocalTextStyle.current
                )
            }
        },
    )
}

@Preview
@Composable
private fun LocationWeatherSheetPreview() {
    MeteoraTheme {
        LocationWeatherSheet(PreviewLocationWeatherSheetComponent())
    }
}
