package com.example.andiwijaya.submission3.teams.detail.players

import com.example.andiwijaya.submission3.TestContextProvider
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.api.TheSportDBApi
import com.example.andiwijaya.submission3.model.Player
import com.example.andiwijaya.submission3.model.PlayerResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {

    @Mock
    private lateinit var view: PlayerView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var playerPresenter: PlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playerPresenter = PlayerPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPlayers() {
        val matches = mutableListOf<Player>()
        val response = PlayerResponse(matches)
        val id = "133932"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getPlayersByTeamId(id)).await(),
                    PlayerResponse::class.java
                )
            ).thenReturn(response)

            playerPresenter.getPlayers(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showPlayerDetail(matches)
            Mockito.verify(view).hideLoading()
        }
    }
}