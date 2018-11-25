package com.example.andiwijaya.submission3.teams.detail.players.detail

import android.util.Log
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.PlayerDetailResponse
import com.example.andiwijaya.submission3.model.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class PlayerDetailPresenter(
    private val view: PlayerDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getPlayerDetail(playerId: String) {
        view.showLoading()

        GlobalScope.async (Dispatchers.Main){
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getPlayerDetail(playerId)).await(),
                PlayerDetailResponse::class.java
            )

            view.showPlayerDetail(data.players)
            view.showLoading()
        }
    }

    fun getHomeBadge(teamId: String) {
        view.showLoading()

        GlobalScope.async(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetail(teamId)).await(),
                TeamResponse::class.java
            )

            Log.d("H", data.toString())

            view.showBadge(data.teams)
            view.hideLoading()
        }
    }
}