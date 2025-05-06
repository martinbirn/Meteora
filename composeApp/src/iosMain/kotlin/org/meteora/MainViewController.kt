package org.meteora

import androidx.compose.ui.window.ComposeUIViewController
import org.meteora.presentation.screens.weather.WeatherScreen

fun MainViewController() = ComposeUIViewController { WeatherScreen() }