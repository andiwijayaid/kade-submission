package com.example.andiwijaya.submission3.teams.detail.players.detail

import com.example.andiwijaya.submission3.TestContextProvider
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.Player
import com.example.andiwijaya.submission3.model.PlayerResponse
import com.example.andiwijaya.submission3.model.Team
import com.example.andiwijaya.submission3.model.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerDetailPresenterTest {

    @Mock
    private lateinit var view: PlayerDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var playerDetailPresenter: PlayerDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playerDetailPresenter = PlayerDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPlayerDetail() {
        val player = mutableListOf<Player>()
        val response = PlayerResponse(player)
        val id = "34161092"

        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getPlayerDetailById(id)).await(),
                    PlayerResponse::class.java
                )
            ).thenReturn(response)

            playerDetailPresenter.getPlayerDetail(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showPlayerDetail(player)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getBadge() {
        val team = mutableListOf<Team>()
        val response = TeamResponse(team)
        val id = "133932"

        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamByIdDetail(id)).await(),
                    TeamResponse::class.java
                )
            ).thenReturn(response)

            playerDetailPresenter.getPlayerDetail(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showBadge(team)
            Mockito.verify(view).hideLoading()
        }
    }
}