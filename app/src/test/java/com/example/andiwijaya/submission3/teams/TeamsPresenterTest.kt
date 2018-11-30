package com.example.andiwijaya.submission3.teams

import com.example.andiwijaya.submission3.TestContextProvider
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.Team
import com.example.andiwijaya.submission3.model.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamsPresenterTest {

    @Mock
    private lateinit var view: TeamsView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var teamsPresenter: TeamsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamsPresenter = TeamsPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        val teams = mutableListOf<Team>()
        val response = TeamResponse(teams)
        val id = "4328"

        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamByLeagueId(id)).await(),
                    TeamResponse::class.java
                )
            ).thenReturn(response)

            teamsPresenter.getTeamList(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showListTeam(teams)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getTeamByNameList() {
        val teams = mutableListOf<Team>()
        val response = TeamResponse(teams)
        val teamName = "Man United"

        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamByName(teamName)).await(),
                    TeamResponse::class.java
                )
            ).thenReturn(response)

            teamsPresenter.getTeamByNameList(teamName)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showListTeam(teams)
            Mockito.verify(view).hideLoading()
        }
    }

}