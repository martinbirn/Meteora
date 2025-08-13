@file:OptIn(ExperimentalMaterial3Api::class)

package org.meteora.presentation.decompose.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.drop

/**
 * A wrapper around Material 3 ModalBottomSheet for displaying BottomSheetContentComponent.
 *
 * It functions like a dialog, displaying a popup over the entire application
 * and doesn't require wrapping other content.
 *
 * This is the foundation for integration with any navigation system, as it accepts
 * a `State<BottomSheetContentComponent?>` without being tied to Decompose.
 *
 * @param sheetContentComponentState The state holding the current BottomSheetContentComponent to display, or null if none.
 * @param onDismiss Callback invoked when the bottom sheet is dismissed by the user (e.g., by swiping down or clicking outside).
 * @param modifier Optional [Modifier] for the bottom sheet.
 * @param sheetState The state of the bottom sheet, controlling its visibility and position.
 * @param key A unique string key for logging purposes.
 * @param sheetMaxWidth The maximum width of the bottom sheet.
 * @param shape The shape of the bottom sheet.
 * @param containerColor The background color of the bottom sheet.
 * @param contentColor The preferred color for content inside the bottom sheet.
 * @param tonalElevation The tonal elevation of the bottom sheet.
 * @param scrimColor The color of the scrim that overlays content behind the bottom sheet.
 * @param dragHandle Optional composable to display as the drag handle for the bottom sheet.
 * @param contentWindowInsets Insets to be applied to the content of the bottom sheet.
 * @param properties Additional properties for customizing the behavior of the modal bottom sheet.
 * @param content The composable content to display inside the bottom sheet, receiving the current [BottomSheetContentComponent].
 *
 * @see <a href="https://github.com/alaershov/sample-mars-colony/blob/master/bottom-sheet/src/main/java/com/alaershov/mars_colony/bottom_sheet/material3/ComponentModalBottomSheet.kt">GitHub Source</a>
 */
@Composable
internal fun ComponentModalBottomSheet(
    sheetContentComponentState: State<BottomSheetContentComponent?>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberComponentModalBottomSheetState(sheetContentComponentState),
    key: String = "ComponentMBS",
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomSheetDefaults.Elevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    content: @Composable (ColumnScope.(BottomSheetContentComponent) -> Unit),
) {
    val currentOnDismiss by rememberUpdatedState(onDismiss)

    // The current BottomSheet state from navigation.
    val sheetContentSlot: BottomSheetContentComponent? by sheetContentComponentState

    // The last remembered BottomSheet content.
    // Unlike the sheetState, we update this field manually.
    // This is necessary so that when the BottomSheet is programmatically closed (navigation.dismiss()),
    // its content doesn't disappear immediately because bottomSheet.child becomes null.
    // In latestChildInstance we keep the last active Child component,
    // and only clear it after modalBottomSheetState.hide() has completed,
    // i.e., when the bottom sheet hide animation finishes.
    var latestChildInstance: BottomSheetContentComponent? by remember {
        mutableStateOf(sheetContentComponentState.value)
    }

    // Launch an effect when the navigation slot state changes.
    LaunchedEffect(sheetContentSlot) {
        log("$key: LaunchedEffect(sheetContentSlot) start $sheetContentSlot")

        val instance = sheetContentSlot

        if (instance == null) {
            // If the slot becomes empty (component removed from navigation), we need to hide the ModalBottomSheet
            // so that during the hide animation, the content of the removed component is still displayed.
            // To achieve this, we first call hide(), and only after that clear latestChildInstance.
            if (sheetState.isVisible) {
                try {
                    log("$key: hide(): start, instance=null, latestChildInstance=$latestChildInstance")
                    sheetState.hide()
                    log("$key: hide(): finish, instance=null, latestChildInstance=$latestChildInstance")
                } catch (e: Exception) {
                    // Coroutine cancellation here is expected (recomposition and other reasons),
                    // so we log it as a regular message rather than a fatal.
                    log(
                        "$key: hide(): exception, instance=null, latestChildInstance=$latestChildInstance",
                        e
                    )
                }
            } else {
                log("$key: hide(): ignore because invisible, instance=null, latestChildInstance=$latestChildInstance")
            }
        } else {
            // If a component appears in the navigation slot, remember it for subsequent hiding,
            // and simply show the BottomSheet without additional actions.
            latestChildInstance = instance

            if (!sheetState.isVisible) {
                try {
                    log("$key: show(): start, instance=$instance")
                    // A delay is needed so that show() during normal open completes without exception
                    // and we don't close the sheet immediately upon opening.
                    sheetState.show()
                    log("$key: show(): finish, instance=$instance")
                } catch (e: Exception) {
                    // Coroutine cancellation here is expected (recomposition and other reasons),
                    // so we log it as a regular message rather than a fatal.
                    log("$key: show(): exception, instance=$instance", e)
                }
            } else {
                log("$key: show(): ignore because visible, instance=$instance")
            }
        }
    }

    // Monitor the UI state of ModalBottomSheet.
    // When the BottomSheet is hidden, notify the host component so it can dismiss
    // and remove the current Child from the navigation slot.
    LaunchedEffect(sheetState) {
        log("$key: snapshotFlow effect sheetState=$sheetState")

        snapshotFlow {
            // We care about whether the sheet is visible and its motion towards opening or closing
            Triple(
                sheetState.isVisible,
                sheetState.targetValue,
                sheetState.currentValue,
            )
        }
            // On first composition we immediately get false for Hidden
            // so we skip the first emission to avoid closing the sheet immediately
            .drop(1)
            .collect { (isVisible, targetValue, currentValue) ->
                val instance = sheetContentSlot

                val message = """
                    |isVisible=$isVisible
                    |targetValue=$targetValue
                    |currentValue=$currentValue
                """.trimMargin()

                log("$key: snapshotFlow $message, instance=$instance")

                // This state means the sheet is fully hidden and not animating.
                val isSheetCompletelyHidden =
                    !isVisible &&
                            targetValue == SheetValue.Hidden &&
                            currentValue == SheetValue.Hidden

                if (isSheetCompletelyHidden) {
                    // UI is closed; notify externally
                    log("$key: currentOnDismiss(), instance=$instance")
                    currentOnDismiss()

                    log("$key: latestChildInstance = null, instance=$instance")
                    // Clear the temporary state we held during closing
                    latestChildInstance = null
                }
            }
    }

    val childInstance = latestChildInstance

    if (childInstance != null) {
        ModalBottomSheet(
            onDismissRequest = {
                // It's not yet clear what benefit this callback provides
                log("$key: onDismissRequest")
            },
            modifier = modifier,
            sheetState = sheetState,
            sheetMaxWidth = sheetMaxWidth,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            scrimColor = scrimColor,
            dragHandle = dragHandle,
            contentWindowInsets = contentWindowInsets,
            properties = properties,
            content = {
                content(childInstance)
            }
        )
    }
}

@Composable
private fun rememberComponentModalBottomSheetState(
    sheetContentComponentState: State<BottomSheetContentComponent?>,
): SheetState {
    return rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { sheetValue ->
            if (sheetValue == SheetValue.Hidden) {
                val instance = sheetContentComponentState.value
                instance?.bottomSheetContentState?.value?.isDismissAllowed ?: true
            } else {
                true
            }
        }
    )
}

private fun log(message: String, throwable: Throwable? = null) {
    if (throwable != null) {
        Napier.d(message, throwable, tag = "ComponentMBS")
    } else {
        Napier.d(message, tag = "ComponentMBS")
    }
}
