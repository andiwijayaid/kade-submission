package com.example.andiwijaya.submission3.matches

import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.EventResponse
import com.example.andiwijaya.submission3.model.MatchResponse
import com.example.andiwijaya.submission3.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchesPresenter(
    private val view: MatchesView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getPastMatchList(id: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getLastMatchByLeagueId(id)).await(),
                MatchResponse::class.java
            )

            view.showListMatch(data.events)
            view.hideLoading()
        }
    }

    fun getNextMatchList(id: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getNextMatchByLeagueId(id)).await(),
                MatchResponse::class.java
            )

            view.showListMatch(data.events)
            view.hideLoading()
        }
    }

    fun getEventList(query: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEvent(query)).await(),
                EventResponse::class.java
            )

            if (!data.event.isNullOrEmpty()) {
                view.showListMatch(data.event)
            }
            view.hideLoading()
        }
    }
}