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

@SuppressLint("SimpleDateFormat")
fun formatTimeToGMT(time: String): String {

    val df = SimpleDateFormat("HH:mm")
    val formattedTime = df.parse(time)
    df.timeZone = TimeZone.getTimeZone("GMT+07")

    return df.format(formattedTime)
}

@SuppressLint("SimpleDateFormat")
fun formatDate(date: String): String {
    val df = SimpleDateFormat("yyyy-MM-dd")
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

fun splitDateString(date: String, time: String): Long {
    val dateList = date.split("-")
    val timeList = time.split(":")

    val mDate = Calendar.getInstance()
    mDate.set(
        dateList[0].toInt(),
        dateList[1].toInt() - 1,
        dateList[2].toInt(),
        timeList[0].toInt()-1,
        timeList[1].toInt()
    )

    val dateMillis = mDate.timeInMillis

    return dateMillis
}