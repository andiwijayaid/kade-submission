package com.example.andiwijaya.submission3.teams.detail.players

import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.PlayerResponse
import com.example.andiwijaya.submission3.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(
    private val view: PlayerView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getPlayers(teamId: String) {

        GlobalScope.launch(contextPool.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getPlayersByTeamId(teamId)).await(),
                PlayerResponse::class.java
            )

            view.showPlayerDetail(data.player)
            view.hideLoading()
        }

    }

}