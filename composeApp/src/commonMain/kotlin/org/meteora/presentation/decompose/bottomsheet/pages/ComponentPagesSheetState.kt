package org.meteora.presentation.decompose.bottomsheet.pages

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Density
import com.arkivanov.decompose.router.pages.ChildPages
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentComponent

/**
 * State Holder for a list of BottomSheet states. For each
 * BottomSheet, both its logical and UI state are stored simultaneously.
 *
 * This class allows avoiding recreating the UI state of each BottomSheet when
 * the logical `pages` state changes. Instead, it stores remembered UI state instances
 * and updates them so that BottomSheets are not recreated when `pages` is updated.
 */
@Stable
internal class ComponentPagesSheetState(
    private val density: Density
) {

    private val _componentSheetStateListState: MutableState<List<ComponentSheetState>> =
        mutableStateOf(listOf())
    val componentSheetStateListState: State<List<ComponentSheetState>> =
        _componentSheetStateListState

    /**
     * Update the list of BottomSheet states based on the current `pages` state.
     *
     * If new components are added to the list, creates new ComponentSheetState instances for them.
     *
     * If states in the list have changed, updates existing instances without recreation.
     */
    fun update(pages: ChildPages<*, BottomSheetContentComponent>) {
        val oldList = componentSheetStateListState.value

        // save components from inside ComponentSheetState of the old list, because the next operation mutates them
        val oldComponentList = oldList.map { it.componentState.value }

        var newList = pages.items.mapIndexed { index, child ->
            val component = child.instance

            // if we found an existing state for the current position in the list, update it,
            // otherwise create a new one
            val componentSheetState = oldList.getOrNull(index)?.apply { updateComponent(component) }
                ?: ComponentSheetState(
                    initialComponent = component,
                    density = density,
                )
            componentSheetState
        }

        val newComponentList = newList.map { it.componentState.value }

        // For supporting animated closing of the top BottomSheet
        // if we see that the new component list contains the same components as the old one,
        // but the last component was removed - instead of removing it, we null its
        // content in the old list. This triggers the BottomSheet hide animation, after
        // completion of which the state will update once more, and the last empty component will be removed.
        if (oldComponentList.isNotEmpty() && oldComponentList.dropLast(1) == newComponentList) {
            val lastState = oldList.last()
            if (lastState.componentState.value != null) {
                lastState.updateComponent(null)
                newList = newList + lastState
            }
        }
        _componentSheetStateListState.value = newList
    }
}
