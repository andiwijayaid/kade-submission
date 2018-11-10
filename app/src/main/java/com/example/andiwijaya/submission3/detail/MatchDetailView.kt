package com.example.andiwijaya.submission3.detail

import com.example.andiwijaya.submission3.model.Match

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<Match>)
}