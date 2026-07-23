package com.example.healthtracker.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    fun getTodayString(): String {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    fun getTodayDisplayString(): String {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    fun getLast7DaysStrings(): List<String> {
        val today = LocalDate.now()
        return (6 downTo 0).map { daysToSubtract ->
            today.minusDays(daysToSubtract.toLong()).format(DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }

    fun getDayOfWeekNumber(dateString: String): Int {
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE).dayOfWeek.value
    }

    fun calculateAgeOrNull(dobStr: String): Int? {
        return try {
            val formatter = if (dobStr.contains("/")) {
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
            } else {
                java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
            }
            val birthDate = java.time.LocalDate.parse(dobStr, formatter)
            val currentDate = java.time.LocalDate.now()

            var age = currentDate.year - birthDate.year
            if (currentDate.dayOfYear < birthDate.dayOfYear) {
                age--
            }
            age
        } catch (e: Exception) {
            null
        }
    }
}