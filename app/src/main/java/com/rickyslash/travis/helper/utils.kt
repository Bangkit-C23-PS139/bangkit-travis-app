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

fun getTimeFromISODate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    return try {
        val date = inputFormat.parse(dateString)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        dateString
    }
}

fun getRandomMaterialColor(): Int {
    val colors = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0",
        "#42A5F5", "#29B6F6", "#26C6DA", "#26A69A", "#66BB6A", "#9CCC65",
        "#D4E157", "#FF7043", "#8D6E63", "#BDBDBD",
        "#78909C")

    return Color.parseColor(colors[Random.nextInt(colors.size)])
}

fun formatPriceToK(price: Int): String {
    return when {
        price < 1000 -> price.toString()
        price < 1000000 -> "${price / 1000}K"
        else -> "${price / 1000000}M"
    }
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^[\\w+_.-]+@(?:[a-z\\d-]+\\.)+[a-z]{2,}\$")
    return emailRegex.matches(email)
}

fun getStartDateFromISODate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm • dd MMM yy", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    return try {
        val date = inputFormat.parse(dateString)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        dateString
    }
}

fun getFirstWord(sentence: String): String {
    val words = sentence.trim().split(" ")
    return if (words.isNotEmpty()) words[0] else ""
}

fun convertSnakeCaseToSentence(snakeCase: String): String {
    return snakeCase.split("_")
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}

fun removeKabupatenKota(s: String): String {
    return s.replace("Kabupaten ", "")
        .replace("Kota ", "")
}

fun addKabupatenKotaPrefix(city: String): String {
    if (!getFirstWord(city).equals("Kota", ignoreCase = true) && !getFirstWord(city).equals("Kabupaten", ignoreCase = true)) {
        var area = "Kota"
        if (city.equals("Bantul", ignoreCase = true) ||
            city.equals("Gunung Kidul", ignoreCase = true) ||
            city.equals("Kulon Progo", ignoreCase = true) ||
            city.equals("Sleman", ignoreCase = true) ||
            city.equals("Kepulauan Seribu", ignoreCase = true)
        ) {
            area = "Kabupaten"
        } else if (city.equals("Yogyakarta", ignoreCase = true) ||
            city.equals("Jakarta Barat", ignoreCase = true) ||
            city.equals("Jakarta Pusat", ignoreCase = true) ||
            city.equals("Jakarta Selatan", ignoreCase = true) ||
            city.equals("Jakarta Timur", ignoreCase = true) ||
            city.equals("Jakarta Utara", ignoreCase = true)
        ) {
            area = "Kota"
        }
        return "$area ${city.split(" ").joinToString(" ") { it -> it.lowercase().replaceFirstChar { it.uppercase() } }}"
    } else {
        return city
    }
}

fun generateRandomSeed(length: Int): String {
    val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { chars.random() }
        .joinToString("")
}

fun getHomeImage(): String {
    val locations = listOf("Yogyakarta", "Bali", "Jakarta", "Indonesia")
    val randomIndex = Random.nextInt(locations.size)
    return "https://source.unsplash.com/1024x768/?${locations[randomIndex]}"
}

fun filterFoodPreference(travelPreference: List<String>): List<String> {
    return travelPreference.filter { item ->
        item in setOf(
            "makanan_chinese",
            "makanan_jepang",
            "makanan_tradisional",
            "makanan_western",
            "pedas",
            "daging"
        )
    }
}

fun filterNotFoodPreference(travelPreference: List<String>): List<String> {
    return travelPreference.filterNot { item ->
        item in setOf(
            "makanan_chinese",
            "makanan_jepang",
            "makanan_tradisional",
            "makanan_western",
            "pedas",
            "daging"
        )
    }
}

fun extractDestinationKeyword(s: String): String {
    val regex = Regex("'(.*?)'")
    val matchResult = regex.find(s)
    return matchResult?.groupValues?.getOrNull(1) ?: "Destinasi"
}