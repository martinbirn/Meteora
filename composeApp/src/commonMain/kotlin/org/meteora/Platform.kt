package org.meteora

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform