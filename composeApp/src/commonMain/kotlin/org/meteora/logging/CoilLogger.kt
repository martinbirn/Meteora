package org.meteora.logging

import coil3.util.Logger
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier

class CoilLogger(override var minLevel: Logger.Level = Logger.Level.Debug) : Logger {

    override fun log(tag: String, level: Logger.Level, message: String?, throwable: Throwable?) {
        if (message != null) {
            Napier.log(
                priority = level.toLogLevel(),
                tag = tag,
                message = message
            )
        }

        if (throwable != null) {
            Napier.log(
                priority = level.toLogLevel(),
                tag = tag,
                throwable = throwable,
                message = throwable.stackTraceToString()
            )
        }
    }

    private fun Logger.Level.toLogLevel() = when (this) {
        Logger.Level.Verbose -> LogLevel.VERBOSE
        Logger.Level.Debug -> LogLevel.DEBUG
        Logger.Level.Info -> LogLevel.INFO
        Logger.Level.Warn -> LogLevel.WARNING
        Logger.Level.Error -> LogLevel.ERROR
    }
}