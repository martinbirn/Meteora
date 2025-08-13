package org.meteora.presentation.decompose.bottomsheet

import kotlinx.coroutines.flow.StateFlow

/**
 * A common interface for modal Bottom Sheet components.
 *
 * This interface defines the contract for components that can be displayed
 * within a Bottom Sheet. It provides a way to observe the state of the
 * Bottom Sheet content.
 *
 * @see <a href="https://github.com/alaershov/sample-mars-colony/blob/master/bottom-sheet/src/main/java/com/alaershov/mars_colony/bottom_sheet/BottomSheetContentComponent.kt">GitHub Source</a>
 */
interface BottomSheetContentComponent {

    val bottomSheetContentState: StateFlow<BottomSheetContentState>
}
