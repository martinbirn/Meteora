package org.meteora.presentation.decompose.bottomsheet.pages.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.ChildNavState
import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.KSerializer

/**
 * Returns RESUMED status if the page is selected, and CREATED otherwise.
 *
 * This is required for BottomSheet navigation based on Pages,
 * to keep the entire stack of opened BottomSheets visible.
 *
 * Unlike the standard [com.arkivanov.decompose.router.pages.getDefaultPageStatus],
 * which returns DESTROYED for all pages that are not neighbors of the selected page.
 */
fun getBottomSheetPageStatus(index: Int, pages: Pages<*>): ChildNavState.Status {
    return if (index == pages.selectedIndex) {
        ChildNavState.Status.RESUMED
    } else {
        ChildNavState.Status.CREATED
    }
}

/**
 * Creates ChildPages for use with BottomSheet.
 */
inline fun <reified C : Any, T : Any> ComponentContext.bottomSheetPages(
    source: NavigationSource<PagesNavigation.Event<C>>,
    serializer: KSerializer<C>?,
    noinline initialPages: () -> Pages<C> = { Pages() },
    key: String = "BottomSheetChildPages",
    noinline pageStatus: (index: Int, Pages<C>) -> ChildNavState.Status = ::getBottomSheetPageStatus,
    handleBackButton: Boolean = false,
    noinline childFactory: (configuration: C, ComponentContext) -> T,
): Value<ChildPages<C, T>> =
    childPages(
        source = source,
        serializer = serializer,
        initialPages = initialPages,
        key = key,
        pageStatus = pageStatus,
        handleBackButton = handleBackButton,
        childFactory = childFactory,
    )
