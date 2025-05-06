@file:Suppress("MemberVisibilityCanBePrivate")

package org.meteora.logging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob

class AppLogger {

    companion object {
        const val TAG_API = "API"
        const val TAG_KOIN = "Koin"
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun setup() {
        //Napier.base(DebugAntilog())
    }

}
