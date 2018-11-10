package com.example.andiwijaya.submission3.api

import com.example.andiwijaya.submission3.BuildConfig

object TheSportDBApi {

    fun getLastMatch(id: String): String {
        return "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/eventspastleague.php?id=$id"
    }

    fun getNextMatch(id: String): String {
        return "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/eventsnextleague.php?id=$id"
    }

    fun getMatchDetail(fileName: String): String {
        return "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/searchfilename.php?e=$fileName"
    }
}