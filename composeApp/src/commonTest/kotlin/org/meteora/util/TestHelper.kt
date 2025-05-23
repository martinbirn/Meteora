package org.meteora.util

import org.meteora.cache.DatabaseDriverFactory

expect abstract class RobolectricTests()

internal expect fun getDatabaseDriverFactory(): DatabaseDriverFactory