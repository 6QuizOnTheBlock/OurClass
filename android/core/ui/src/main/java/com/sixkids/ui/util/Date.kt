package com.sixkids.ui.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatToMonthDayTime(): String {
    val formatter = DateTimeFormatter.ofPattern("MM.dd HH:mm")
    return this.format(formatter)
}

fun LocalDateTime.formatToMonthDayTimeKorean(): String {
    val formatter = DateTimeFormatter.ofPattern("M월 d일 a h시 m분")
    return this.format(formatter)
}

fun LocalDate.formatToDayMonthYear(): String {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return this.format(formatter)
}

fun LocalTime.formatToHourMinute(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}

fun LocalDateTime.formatToMonthDayKorean(): String {
    val formatter = DateTimeFormatter.ofPattern("M월 d일")
    return this.format(formatter)
}