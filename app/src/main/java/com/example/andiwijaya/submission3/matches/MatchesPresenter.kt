package com.example.andiwijaya.submission3.matches

import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.MatchResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchesPresenter(
    private val view: MatchesView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun getPastMatchList(id: String) {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLastMatch(id)),
                MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showListMatch(data.events)
            }
        }
    }

    fun getNextMatchList(id: String) {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(id)),
                MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showListMatch(data.events)
            }
        }
    }

}
