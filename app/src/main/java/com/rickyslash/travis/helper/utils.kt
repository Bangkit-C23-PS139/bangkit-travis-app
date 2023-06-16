package com.rickyslash.travis.helper

import android.content.ContentResolver
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

private const val MAXIMAL_SIZE = 1000000

val timeStamp: String = SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(System.currentTimeMillis())

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

fun addKabupatenKota(city: String): String {
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

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)

    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)

    outputStream.close()
    inputStream.close()
    return myFile
}

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}