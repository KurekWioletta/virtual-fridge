package com.virtualfridge.virtualfridge.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

fun String.isValidEmail(): Boolean = this.isNotEmpty() &&
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(this).matches()

fun String.isValidDate(): Boolean {
    return if (this.isNotEmpty() && Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$").matcher(this)
                    .matches()
    ) {
        try {
            LocalDate.parse(this, dateTimeFormatter)
            true
        } catch (e: Exception) {
            false
        }
    } else {
        false
    }
}

fun String.isValidPassword(): Boolean = this.isNotEmpty() && this.length >= 6

val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")