package com.example.andiwijaya.submission3.matches

import com.example.andiwijaya.submission3.model.Match

interface MatchesView {

    fun showLoading()
    fun hideLoading()
    fun showListMatch(data: List<Match>)

}