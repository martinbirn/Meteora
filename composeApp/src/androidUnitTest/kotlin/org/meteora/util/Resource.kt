package org.meteora.util

import java.io.File

actual class Resource actual constructor(private val path: String) {
    private val file = File(path)

    actual fun exists(): Boolean = file.exists()

    actual fun readText(): String = runCatching {
        file.readText()
    }.getOrElse { cause ->
        throw FileReadException("$path: No such file or directory", cause)
    }

    actual fun readBytes(): ByteArray = runCatching {
        file.readBytes()
    }.getOrElse { cause ->
        throw FileReadException("$path: No such file or directory", cause)
    }
}
