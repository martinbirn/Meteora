package org.meteora.presentation.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun WeatherScreen() {
    MaterialTheme {
        val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
        val permissionsController: PermissionsController = remember(factory) {
            factory.createPermissionsController()
        }

        BindEffect(permissionsController)

        val viewModel: WeatherViewModel = koinViewModel {
            parametersOf(permissionsController)
        }

        val state by viewModel.state.collectAsState()

        Column {
            Text("temperature: ${state.temp}")
            if (state.error != null) {
                Text("Error: ${state.error}")
            }
        }
    }
}