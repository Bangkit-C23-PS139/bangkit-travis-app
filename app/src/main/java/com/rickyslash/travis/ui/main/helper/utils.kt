package com.rickyslash.travis.ui.main.helper

import java.text.SimpleDateFormat
import java.util.*

fun getDateToday(): String {
    val currentDate = Calendar.getInstance().time
    val dayFormat = if (currentDate.day < 10) "d" else "dd"
    val dateFormat = SimpleDateFormat("$dayFormat MMMM yyyy", Locale.getDefault())
    return dateFormat.format(currentDate)
}