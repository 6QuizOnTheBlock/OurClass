package com.sixkids.data.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter {
    @ToJson
    fun toJson(value: LocalDate): String {
        return FORMATTER.format(value)
    }

    @FromJson
    fun fromJson(value: String): LocalDate {
        return LocalDate.parse(value, FORMATTER)
    }

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE
    }
}