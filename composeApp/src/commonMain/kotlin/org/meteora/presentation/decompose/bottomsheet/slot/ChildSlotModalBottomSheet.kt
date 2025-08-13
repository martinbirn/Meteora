@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("unused")

package org.meteora.presentation.decompose.bottomsheet.slot

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentComponent
import org.meteora.presentation.decompose.bottomsheet.ComponentModalBottomSheet

/**
 * A wrapper around Material 3 ModalBottomSheet for displaying BottomSheetContentComponent.
 *
 * It functions like a dialog, displaying a popup over the entire application
 * and doesn't require wrapping other content.
 *
 * Shows BottomSheet based on ChildSlot from Decompose navigation.
 *
 * @param sheetContentSlotState The state holding the current ChildSlot with BottomSheetContentComponent to display.
 * @param onDismiss Callback invoked when the bottom sheet is dismissed by the user (e.g., by swiping down or clicking outside).
 * @param modifier Optional [Modifier] for the bottom sheet.
 * @param key A unique string key for logging purposes.
 * @param shape The shape of the bottom sheet.
 * @param containerColor The background color of the bottom sheet.
 * @param contentColor The preferred color for content inside the bottom sheet.
 * @param tonalElevation The tonal elevation of the bottom sheet.
 * @param scrimColor The color of the scrim that overlays content behind the bottom sheet.
 * @param dragHandle Optional composable to display as the drag handle for the bottom sheet.
 * @param contentWindowInsets Insets to be applied to the content of the bottom sheet.
 * @param content The composable content to display inside the bottom sheet, receiving the current [BottomSheetContentComponent].
 *
 * @see <a href="https://github.com/alaershov/sample-mars-colony/blob/master/bottom-sheet/src/main/java/com/alaershov/mars_colony/bottom_sheet/material3/slot/ChildSlotModalBottomSheet.kt">GitHub Source</a>
 */
@Composable
fun ChildSlotModalBottomSheet(
    sheetContentSlotState: Value<ChildSlot<*, BottomSheetContentComponent>>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    key: String = "ChildSlotMBS",
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomSheetDefaults.Elevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },
    content: @Composable (ColumnScope.(BottomSheetContentComponent) -> Unit),
) {
    val sheetContentSlot: ChildSlot<*, BottomSheetContentComponent> by sheetContentSlotState.subscribeAsState()

    val sheetContentComponentState: State<BottomSheetContentComponent?> =
        rememberUpdatedState(sheetContentSlot.child?.instance)

    ComponentModalBottomSheet(
        sheetContentComponentState = sheetContentComponentState,
        onDismiss = onDismiss,
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
