package org.meteora.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.meteora.cache.AndroidDatabaseDriverFactory
import org.meteora.cache.DatabaseDriverFactory

val databaseModule = module {
    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(context = androidContext()) }
}
