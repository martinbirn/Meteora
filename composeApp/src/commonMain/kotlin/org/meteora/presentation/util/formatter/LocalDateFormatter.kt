package org.meteora.presentation.util.formatter

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DayOfWeekNames

val dayOfWeekFormatter = LocalDate.Format {
    dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
}