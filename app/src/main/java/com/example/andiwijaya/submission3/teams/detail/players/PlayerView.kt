package com.example.andiwijaya.submission3.teams.detail.players

import com.example.andiwijaya.submission3.model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun getDataFromAPI()
    fun showPlayerDetail(data: List<Player>)
}