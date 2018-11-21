package com.example.andiwijaya.submission3.teams.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.favorites.FavoritesViewPagerAdapter
import com.example.andiwijaya.submission3.matches.nextmatch.NextMatchFragment
import com.example.andiwijaya.submission3.teams.overview.OverviewFragment
import kotlinx.android.synthetic.main.activity_team_detail.*

class TeamDetailActivity : AppCompatActivity() {

    val nama = "A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        val mAdapter = FavoritesViewPagerAdapter(supportFragmentManager)
        mAdapter.addFragment(OverviewFragment(), "overview")
        mAdapter.addFragment(NextMatchFragment(), "players")

        viewPager.adapter = mAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
