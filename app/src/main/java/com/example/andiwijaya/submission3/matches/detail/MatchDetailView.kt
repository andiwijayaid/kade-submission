package com.example.andiwijaya.submission3.matches.detail

import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.model.Team

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun getMatchDetailFromAPI()
    fun getHomeBadgeFromAPI()
    fun getAwayBadgeFromAPI()
    fun showMatchDetail(data: List<Match>)
    fun showHomeBadge(data: List<Team>)
    fun showAwayBadge(data: List<Team>)
}