package com.example.andiwijaya.submission3.detail

import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.MatchDetailResponse
import com.example.andiwijaya.submission3.model.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson) {

    fun getMatchDetail(fileName: String) {
        view.showLoading()

        GlobalScope.async(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatchDetail(fileName)).await(),
                MatchDetailResponse::class.java)

            view.showMatchDetail(data.event)
            view.hideLoading()
        }
    }
}