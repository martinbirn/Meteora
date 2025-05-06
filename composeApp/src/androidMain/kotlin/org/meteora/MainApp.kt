package org.meteora

import android.app.Application
import org.meteora.di.initKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}