package org.meteora

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.meteora.di.databaseModule
import org.meteora.di.sharedModules

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(databaseModule + sharedModules)
        }
    }
}