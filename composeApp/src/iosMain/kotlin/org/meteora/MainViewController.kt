package org.meteora

import androidx.compose.ui.window.ComposeUIViewController
import org.meteora.presentation.weather.WeatherScreen

fun MainViewController() = ComposeUIViewController { WeatherScreen() }