package com.sixkids.ui.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatToMonthDayTime(): String {
    val formatter = DateTimeFormatter.ofPattern("MM.dd HH:mm")
    return this.format(formatter)
}
