package com.example.andiwijaya.submission3.util

import android.annotation.SuppressLint
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun formatTimeToGMT(time: String): String {

    val df = SimpleDateFormat("HH:mm")
    val formattedTime = df.parse(time)
    df.timeZone = TimeZone.getTimeZone("GMT+07")

    return df.format(formattedTime)
}

@SuppressLint("SimpleDateFormat")
fun formatDate(date: String): String {
    val df = SimpleDateFormat("dd/MM/yyyy")
    val result = df.parse(date)
    val dayFormat = SimpleDateFormat("E")
    val namaHari = dayFormat.format(result)

    val newDateFormat = SimpleDateFormat("dd MMM yy")
    val newDate = newDateFormat.format(result)

    return "$namaHari, $newDate"
}

fun replaceSemicolonWithNewRow(string: String): String {
    return string.replace(";", "\n")
}