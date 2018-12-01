package com.example.andiwijaya.submission3.teams.detail.players.detail

import com.example.andiwijaya.submission3.model.Player
import com.example.andiwijaya.submission3.model.Team

interface PlayerDetailView {

    fun showLoading()
    fun hideLoading()
    fun getPlayerDetailFromAPI()
    fun getBadgeFromAPI()
    fun showPlayerDetail(data: List<Player>)
    fun showBadge(data: List<Team>)
}