@file:OptIn(ExperimentalMaterial3Api::class)

package org.meteora.presentation.decompose.bottomsheet.pages

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentComponent
import org.meteora.presentation.decompose.bottomsheet.ComponentModalBottomSheet

/**
 * A Material 3 ModalBottomSheet for displaying a stack of BottomSheetContentComponents.
 *
 * It functions like a dialog, displaying a popup over the entire application
 * and doesn't require wrapping other content.
 *
 * Shows multiple BottomSheets stacked on top of each other,
 * based on ChildPages from Decompose navigation.
 */
@Composable
fun ChildPagesModalBottomSheet(
    sheetContentPagesState: Value<ChildPages<*, BottomSheetContentComponent>>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    key: String = "ChildPagesMBS",
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomSheetDefaults.Elevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },
    content: @Composable (ColumnScope.(BottomSheetContentComponent) -> Unit),
) {
    // observe the ChildPages state
    // value updates with every change to the navigation stack - addition or removal of pages.
    val sheetContentStack: ChildPages<*, BottomSheetContentComponent> by sheetContentPagesState.subscribeAsState()

    // initialize and remember the state of all BottomSheets in this navigation stack
    val density = LocalDensity.current

    val state = remember(sheetContentPagesState, density) {
        ComponentPagesSheetState(density)
    }
    // update this state on every navigation stack change
    LaunchedEffect(sheetContentStack) {
        state.update(sheetContentStack)
    }

    // state contains the current and updated state for displaying all BottomSheets,
    // so we simply draw them one after another in a list. Unnecessary recompositions of previous list elements
    // should not occur because their states are remembered in this list and don't change when adding
    // or removing pages from the top of the stack.
    state.componentSheetStateListState.value.forEach { componentSheetState ->
        ComponentModalBottomSheet(
            sheetContentComponentState = componentSheetState.componentState,
            sheetState = componentSheetState.sheetState,
            onDismiss = {
                if (componentSheetState.componentState.value != null) {
                    // Component instance will be non-null at this moment
                    // if dismiss was called from the UI side.
                    // In this case, the component needs to be removed from the navigation stack,
                    // so we call onDismiss()

                    onDismiss()
                } else {
                    // Component instance will be null at this moment
                    // if dismiss was called from the navigation logic side.
                    // In this case, we need to update the stack once more to remove
                    // the top state with empty component from it.

                    state.update(sheetContentStack)
                }
            },
            modifier = modifier,
            key = key,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            scrimColor = scrimColor,
            dragHandle = dragHandle,
            contentWindowInsets = contentWindowInsets,
            properties = ModalBottomSheetDefaults.properties,
            content = content,
        )
    }
}
