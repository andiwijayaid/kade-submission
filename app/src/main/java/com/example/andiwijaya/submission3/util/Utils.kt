package com.example.andiwijaya.submission3.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import java.text.SimpleDateFormat
import java.util.*
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.app.FragmentActivity
import android.util.Log


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

fun convertToMillis(date: String, time: String): Long {
    val dateList = date.split("-")
    val timeInGMT = formatTimeToGMT(time)
    val timeList = timeInGMT.split(":")

    val mDate = Calendar.getInstance()
    mDate.set(
        dateList[0].toInt(),
        dateList[1].toInt() - 1,
        dateList[2].toInt(),
        timeList[0].toInt(),
        timeList[1].toInt()
    )

    val dateMillis = mDate.timeInMillis

    return dateMillis
}

@SuppressLint("ServiceCast")
fun checkInternetConnection(activity: FragmentActivity?) : Boolean {
    val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (connectivityManager is ConnectivityManager) {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected ?: false
    } else false
}