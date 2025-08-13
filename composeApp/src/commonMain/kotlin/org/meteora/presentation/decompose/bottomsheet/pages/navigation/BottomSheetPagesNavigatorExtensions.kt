package org.meteora.presentation.decompose.bottomsheet.pages.navigation

import com.arkivanov.decompose.router.pages.PagesNavigator

/**
 * Analog of `StackNavigator<C>.pushNew()` for PagesNavigator.
 *
 * Adds the given [configuration] as the last page in Pages and makes it selected.
 *
 * Does nothing if [configuration] is already the last page.
 *
 * Decompose will throw an exception if [configuration] already exists in the stack (except as the last page).
 *
 * @param onComplete called when navigation is completed (synchronously or asynchronously).
 * `isSuccess` will be `true` if the page was added to the stack.
 */
fun <C : Any> PagesNavigator<C>.pushNew(
    configuration: C,
    onComplete: (isSuccess: Boolean) -> Unit = {},
) {
    navigate(
        transformer = { pages ->
            val newItems = if (pages.items.lastOrNull() == configuration) {
                pages.items
            } else {
                pages.items + configuration
            }
            pages.copy(
                items = newItems,
                selectedIndex = newItems.size - 1,
            )
        },
        onComplete = { newPages, oldPages ->
            onComplete(newPages.items.size > oldPages.items.size)
        }
    )
}

/**
 * Analog of `StackNavigator<C>.pop()` for PagesNavigator.
 *
 * Removes the last page from the stack.
 *
 * @param onComplete called when navigation is completed (synchronously or asynchronously).
 * `isSuccess` will be `true` if there was at least one page in the stack,
 * and it was removed as a result of this method call.
 */
fun <C : Any> PagesNavigator<C>.pop(onComplete: (isSuccess: Boolean) -> Unit = {}) {
    navigate(
        transformer = { pages ->
            val newItems = pages.items
                .takeIf { it.isNotEmpty() }
                ?.dropLast(1)
                ?: pages.items
            pages.copy(
                items = newItems,
                selectedIndex = newItems.size - 1,
            )
        },
        onComplete = { newPages, oldPages ->
            onComplete(newPages.items.size < oldPages.items.size)
        }
    )
}
