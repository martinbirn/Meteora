package org.meteora.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.Ref

@Composable
inline fun <T> AnimatedValueVisibility(
    value: T?,
    modifier: Modifier = Modifier,
    enter: EnterTransition = fadeIn() + expandIn(),
    exit: ExitTransition = shrinkOut() + fadeOut(),
    label: String = "AnimatedVisibility",
    crossinline content: @Composable (T) -> Unit
) {
    val ref = remember { Ref<T>() }
    ref.value = value ?: ref.value
    AnimatedVisibility(
        visible = value != null,
        modifier = modifier,
        enter = enter,
        exit = exit,
        label = label,
        content = {
            ref.value?.let { value ->
                content(value)
            }
        }
    )
}
