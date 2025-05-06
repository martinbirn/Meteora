package org.meteora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.meteora.presentation.weather.WeatherScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherScreen()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    WeatherScreen()
}