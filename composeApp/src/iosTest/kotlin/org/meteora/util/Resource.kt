@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

package org.meteora.util

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.value
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSDataReadingUncached
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.stringWithContentsOfFile

@OptIn(UnsafeNumber::class)
actual class Resource actual constructor(private val path: String) {
    private val absolutePath = NSBundle.mainBundle.pathForResource(
        path.substringBeforeLast("."),
        path.substringAfterLast(".")
    )

    actual fun exists(): Boolean = absolutePath != null

    actual fun readText(): String = memScoped {
        if (absolutePath == null) {
            throw FileReadException("$path: No such file or directory")
        }
        val error = alloc<ObjCObjectVar<NSError?>>()
        NSString.stringWithContentsOfFile(absolutePath, NSUTF8StringEncoding, error.ptr)
            ?: throw FileReadException("$path: Read failed: ${error.value}")
    }

    actual fun readBytes(): ByteArray = memScoped {
        if (absolutePath == null) {
            throw FileReadException("$path: No such file or directory")
        }
        val error = alloc<ObjCObjectVar<NSError?>>()
        val data = NSData.dataWithContentsOfFile(absolutePath, NSDataReadingUncached, error.ptr)
        val bytes = data?.bytes ?: throw FileReadException("$path: Read failed: ${error.value}")
        bytes.readBytes(data.length.toInt())
    }
}
