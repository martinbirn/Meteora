package org.meteora.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

fun Modifier.ifTrue(
    condition: Boolean,
    ifTrue: () -> Modifier
): Modifier {
    return if (condition) {
        then(ifTrue())
    } else {
        this
    }
}

@Composable
fun Modifier.ifTrueComposable(
    condition: Boolean,
    ifTrue: @Composable () -> Modifier
): Modifier {
    return if (condition) {
        then(ifTrue())
    } else {
        this
    }
}

fun Modifier.ifTrue(
    condition: Boolean,
    modifier: Modifier
): Modifier {
    return if (condition) {
        then(modifier)
    } else {
        this
    }
}

inline fun <T> Modifier.ifNotNull(
    value: T?,
    ifNotNull: (T) -> Modifier
): Modifier {
    return if (value != null) {
        then(ifNotNull(value))
    } else {
        this
    }
}
