package org.meteora.di

import org.koin.dsl.module
import org.meteora.cache.DatabaseDriverFactory
import org.meteora.cache.IOSDatabaseDriverFactory

actual fun platformModule() = module {
    single<DatabaseDriverFactory> { IOSDatabaseDriverFactory() }
}
