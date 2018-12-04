package com.example.andiwijaya.submission3.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.app.FragmentActivity
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
fun formatDateTimeToGMT(date: String, time: String): String {
    val dateTime = "$date $time"

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT 7")
    return simpleDateFormat.parse(dateTime).toString()
}

fun getDateOnly(date: String): String {
    val dateList = date.split(" ")
    return "${dateList[0]}, ${dateList[2]} ${dateList[1]} ${dateList[5]}"
}

fun getTimeOnly(time: String): String {
    val dateList = time.split(" ")
    val mTime = dateList[3]
    val timeList = mTime.split(":")
    return "${timeList[0]}:${timeList[1]}"
}

fun replaceSemicolonWithNewRow(string: String): String {
    return string.replace(";", "\n")
}

@SuppressLint("SimpleDateFormat")
fun convertToMillis(date: String, time: String): Long {
    val dateTime = formatDateTimeToGMT(date, time)

    val dateList = getDateOnly(dateTime).split(" ")
    val timeList = getTimeOnly(dateTime).split(":")

    val mDate = Calendar.getInstance()

    val df = SimpleDateFormat("MMM").parse(dateList[2])
    val calendar = Calendar.getInstance()
    calendar.time = df
    val monthToInt = calendar[Calendar.MONTH]

    mDate.set(
        dateList[3].toInt(),
        monthToInt,
        dateList[1].toInt(),
        timeList[0].toInt(),
        timeList[1].toInt()
    )

    val dateMillis = mDate.timeInMillis

    return dateMillis
}

@SuppressLint("ServiceCast")
fun checkInternetConnection(activity: FragmentActivity?): Boolean {
    val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (connectivityManager is ConnectivityManager) {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected ?: false
    } else false
}