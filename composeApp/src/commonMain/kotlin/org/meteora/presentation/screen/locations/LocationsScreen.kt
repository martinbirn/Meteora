package org.meteora.presentation.screen.locations

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LocationsScreen() {
    val viewModel: LocationsViewModel = koinViewModel()
}