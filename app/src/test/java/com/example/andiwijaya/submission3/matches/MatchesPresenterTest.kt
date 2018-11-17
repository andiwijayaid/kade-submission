package com.example.andiwijaya.submission3.matches

import com.example.andiwijaya.submission3.TestContextProvider
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.model.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchesPresenterTest {

    @Mock
    private lateinit var view: MatchesView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var matchesPresenter: MatchesPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchesPresenter = MatchesPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPastMatchList() {
        val matches = mutableListOf<Match>()
        val response = MatchResponse(matches)
        val id = "4328"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLastMatch(id)).await(),
                MatchResponse::class.java
                )).thenReturn(response)

            matchesPresenter.getPastMatchList(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showListMatch(matches)
            Mockito.verify(view).hideLoading()
        }
    }

}