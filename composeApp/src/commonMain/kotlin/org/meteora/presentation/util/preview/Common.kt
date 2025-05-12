package org.meteora.presentation.util.preview

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PreviewSharedLayout(
    content: @Composable SharedTransitionScope.(animated: AnimatedContentScope) -> Unit
) {
    SharedTransitionLayout {
        AnimatedContent(Unit) {
            content(this)
        }
    }
}
