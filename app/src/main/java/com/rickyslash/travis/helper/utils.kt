package com.rickyslash.travis.helper

import android.graphics.Color
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

fun getDateToday(): String {
    val currentDate = Calendar.getInstance().time
    val dayFormat = if (currentDate.day < 10) "d" else "dd"
    val dateFormat = SimpleDateFormat("$dayFormat MMMM yyyy", Locale.getDefault())
    return dateFormat.format(currentDate)
}

fun getRandomMaterialColor(): Int {
    val colors = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0",
        "#42A5F5", "#29B6F6", "#26C6DA", "#26A69A", "#66BB6A", "#9CCC65",
        "#D4E157", "#FF7043", "#8D6E63", "#BDBDBD",
        "#78909C")

    return Color.parseColor(colors[Random.nextInt(colors.size)])
}