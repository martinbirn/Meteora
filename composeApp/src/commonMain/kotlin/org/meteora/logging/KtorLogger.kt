package org.meteora.logging

import io.github.aakira.napier.Napier
import io.ktor.client.plugins.logging.Logger

class KtorLogger : Logger {
    override fun log(message: String) {
        Napier.d(message, tag = AppLogger.TAG_API)
    }
}