package com.example.andiwijaya.submission3.matches.detail

import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.model.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest {

    @Mock
    private
    lateinit var view: MatchDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var matchDetailPresenter: MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchDetailPresenter = MatchDetailPresenter(view, apiRepository, gson)
    }

    @Test
    fun getMatchDetail() {

        val matches = mutableListOf<Match>()
        val response = MatchResponse(matches)
        val fileName = "English Premier League 2018-11-10 Chelsea vs Everton"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatchDetail(fileName)).await(),
                MatchResponse::class.java
            )).thenReturn(response)

            matchDetailPresenter.getMatchDetail(fileName)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchDetail(matches)
            Mockito.verify(view).hideLoading()
        }

    }
}