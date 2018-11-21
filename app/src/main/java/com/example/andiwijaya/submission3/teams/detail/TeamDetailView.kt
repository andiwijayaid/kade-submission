package com.example.andiwijaya.submission3.teams.detail

import com.example.andiwijaya.submission3.model.Team

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}