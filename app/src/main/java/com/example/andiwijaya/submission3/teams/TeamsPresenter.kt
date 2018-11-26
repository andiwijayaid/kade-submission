package com.example.andiwijaya.submission3.teams

import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.TeamResponse
import com.example.andiwijaya.submission3.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamsPresenter(
    private val view: TeamsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamList(league: String) {
        view.showLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamByLeagueId(league)).await(),
                TeamResponse::class.java
            )

            view.showListTeam(data.teams)
            view.hideLoading()
        }
    }

    fun getTeamByNameList(name: String) {
        view.showLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamByName(name)).await(),
                TeamResponse::class.java
            )

            view.showListTeam(data.teams)
            view.hideLoading()
        }
    }

}