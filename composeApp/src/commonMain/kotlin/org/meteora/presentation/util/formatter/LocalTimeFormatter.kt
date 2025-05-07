package org.meteora.presentation.util.formatter

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char

val hourMinuteFormatter = LocalTime.Format {
    hour(); char(':'); minute()
}