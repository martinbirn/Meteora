package org.meteora.di

import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.meteora.cache.DatabaseDriverFactory
import org.meteora.cache.IOSDatabaseDriverFactory

val databaseModule = module {
    single<DatabaseDriverFactory> { IOSDatabaseDriverFactory() }
}

fun initKoin() {
    startKoin {
        modules(databaseModule + sharedModules)
    }
}
