package org.meteora.presentation.util

import androidx.compose.ui.Modifier
import org.meteora.presentation.util.shader.clouds_sksl
import org.meteora.presentation.util.shader.snow_sksl

expect fun Modifier.shaderEffect(shaderOption: ShaderOptions): Modifier

enum class ShaderOptions() {
    SNOW(),
    CLOUDS();

    fun toShaderCode() = when (this) {
        SNOW -> snow_sksl
        CLOUDS -> clouds_sksl
    }
}