package org.meteora.presentation.decompose.bottomsheet

/**
 * Represents the state of a Modal Bottom Sheet component.
 *
 * @see <a href="https://github.com/alaershov/sample-mars-colony/blob/master/bottom-sheet/src/main/java/com/alaershov/mars_colony/bottom_sheet/BottomSheetContentState.kt">GitHub Source</a>
 */
interface BottomSheetContentState {

    /**
     * Whether this Bottom Sheet is allowed to be dismissed.
     */
    val isDismissAllowed: Boolean
}
