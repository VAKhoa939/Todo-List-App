package com.example.todolist.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getFormattedDate(locale: Locale, date: Date): String {
    val dateFormatter = SimpleDateFormat(
        "MMM dd, HH:mm",
        locale
    )
    return dateFormatter.format(date)
}