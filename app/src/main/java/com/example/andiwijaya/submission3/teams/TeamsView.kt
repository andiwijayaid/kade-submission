package com.example.andiwijaya.submission3.teams

import com.example.andiwijaya.submission3.model.Team

interface TeamsView {

    fun showLoading()
    fun hideLoading()
    fun getDataFromAPI()
    fun showListTeam(data: List<Team>)

}