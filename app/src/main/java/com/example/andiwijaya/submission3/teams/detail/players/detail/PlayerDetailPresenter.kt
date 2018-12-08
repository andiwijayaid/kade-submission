package com.example.andiwijaya.submission3.teams.detail.players.detail

import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.PlayerDetailResponse
import com.example.andiwijaya.submission3.model.TeamResponse
import com.example.andiwijaya.submission3.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class PlayerDetailPresenter(
    private val view: PlayerDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getPlayerDetail(playerId: String) {
        view.showLoading()

        GlobalScope.async(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getPlayerDetailById(playerId)).await(),
                PlayerDetailResponse::class.java
            )

            if (!data.players.isNullOrEmpty()) {
                view.showPlayerDetail(data.players)
            }
            view.showLoading()
        }
    }

    fun getBadge(teamId: String) {
        view.showLoading()

        GlobalScope.async(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamByIdDetail(teamId)).await(),
                TeamResponse::class.java
            )

            if (!data.teams.isNullOrEmpty()) {
                view.showBadge(data.teams)
            }
            view.hideLoading()
        }
    }
}