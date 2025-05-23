package org.meteora.util

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import org.meteora.cache.AndroidDatabaseDriverFactory
import org.meteora.cache.DatabaseDriverFactory
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
actual abstract class RobolectricTests

internal actual fun getDatabaseDriverFactory(): DatabaseDriverFactory {
    return AndroidDatabaseDriverFactory(ApplicationProvider.getApplicationContext<Application>())
}
