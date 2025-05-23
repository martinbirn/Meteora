package org.meteora.util

import org.meteora.cache.DatabaseDriverFactory
import org.meteora.cache.IOSDatabaseDriverFactory

actual abstract class RobolectricTests

internal actual fun getDatabaseDriverFactory(): DatabaseDriverFactory {
    return IOSDatabaseDriverFactory()
}