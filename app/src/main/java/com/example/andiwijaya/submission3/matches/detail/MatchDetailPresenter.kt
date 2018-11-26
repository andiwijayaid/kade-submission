package com.example.andiwijaya.submission3.matches.detail

import android.util.Log
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.MatchDetailResponse
import com.example.andiwijaya.submission3.model.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MatchDetailPresenter(
    private val view: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun getMatchDetail(fileName: String) {
        view.showLoading()

        GlobalScope.async(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getMatchDetail(fileName)).await(),
                MatchDetailResponse::class.java
            )

            view.showMatchDetail(data.event)
            view.hideLoading()
        }
    }

    fun getHomeBadge(teamId: String) {
        view.showLoading()

        GlobalScope.async(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamByIdDetail(teamId)).await(),
                TeamResponse::class.java
            )

            Log.d("H", data.toString())

            view.showHomeBadge(data.teams)
            view.hideLoading()
        }
    }

    fun getAwayBadge(teamId: String) {
        view.showLoading()

        GlobalScope.async(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamByIdDetail(teamId)).await(),
                TeamResponse::class.java
            )

            Log.d("A", data.toString())

            view.showAwayBadge(data.teams)
            view.hideLoading()
        }
    }
}