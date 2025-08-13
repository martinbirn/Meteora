package org.meteora

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureIcon
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import org.meteora.presentation.decompose.DefaultAppComponent
import org.meteora.presentation.screen.App

fun MainViewController(
    component: DefaultAppComponent,
    backDispatcher: BackDispatcher,
) = ComposeUIViewController {
    PredictiveBackGestureOverlay(
        backDispatcher = backDispatcher,
        backIcon = { progress, _ ->
            PredictiveBackGestureIcon(
                imageVector = Icons.Default.ArrowBackIosNew,
                progress = progress,
            )
        },
        modifier = Modifier.fillMaxSize(),
        endEdgeEnabled = false,
    ) {
        App(component)
    }
}
