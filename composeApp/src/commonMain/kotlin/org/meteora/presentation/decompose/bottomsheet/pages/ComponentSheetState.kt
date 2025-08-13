package org.meteora.presentation.decompose.bottomsheet.pages

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Density
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentComponent

/**
 * State Holder for ComponentModalBottomSheet.
 *
 * Contains simultaneously the logical state of the `BottomSheetContentComponent` and
 * the UI state `SheetState`. This allows storing a list of such paired states
 * and using it in BottomSheet stack implementation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Stable
internal class ComponentSheetState(
    initialComponent: BottomSheetContentComponent?,
    density: Density,
) {
    private val _componentState: MutableState<BottomSheetContentComponent?> =
        mutableStateOf(initialComponent)
    val componentState: State<BottomSheetContentComponent?> = _componentState

    val sheetState: SheetState = SheetState(
        skipPartiallyExpanded = true,
        density = density,
        initialValue = SheetValue.Hidden,
        confirmValueChange = { sheetValue ->
            if (sheetValue == SheetValue.Hidden) {
                val instance = componentState.value
                instance?.bottomSheetContentState?.value?.isDismissAllowed ?: true
            } else {
                true
            }
        },
        skipHiddenState = false,
    )

    fun updateComponent(component: BottomSheetContentComponent?) {
        if (_componentState.value == component) return

        _componentState.value = component
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as ComponentSheetState

        if (componentState != other.componentState) return false
        return sheetState == other.sheetState
    }

    override fun hashCode(): Int {
        var result = componentState.hashCode()
        result = 31 * result + sheetState.hashCode()
        return result
    }

    override fun toString(): String {
        return "${this::class.simpleName}@${hashCode().toHexString()}" +
                "(componentState=$componentState, " +
                "sheetState=$sheetState)"
    }
}
