package com.sixkids.ui.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatToMonthDayTime(): String {
    val formatter = DateTimeFormatter.ofPattern("MM.dd HH:mm")
    return this.format(formatter)
}

fun LocalDate.formatToDayMonthYear(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return this.format(formatter)
}

fun LocalDateTime.formatToHourMinute(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}
