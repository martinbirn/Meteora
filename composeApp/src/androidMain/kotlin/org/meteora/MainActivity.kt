package org.meteora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.defaultComponentContext
import org.meteora.presentation.decompose.DefaultAppComponent
import org.meteora.presentation.screen.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val appComponent = DefaultAppComponent(
            componentContext = defaultComponentContext().childContext("app"),
        )

        setContent {
            App(component = appComponent)
        }
    }
}