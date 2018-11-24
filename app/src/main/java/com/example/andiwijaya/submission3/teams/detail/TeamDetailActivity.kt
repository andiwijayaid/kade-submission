package com.example.andiwijaya.submission3.teams.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.favorites.FavoritesViewPagerAdapter
import com.example.andiwijaya.submission3.matches.nextmatch.NextMatchFragment
import com.example.andiwijaya.submission3.model.Team
import com.example.andiwijaya.submission3.teams.detail.overview.OverviewFragment
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import android.support.design.widget.AppBarLayout


class TeamDetailActivity : AppCompatActivity(), TeamDetailView {
    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showTeamDetail(data: List<Team>) {
        Picasso.get().load(data[0].teamDetailBackground).into(teamBackgroundIV)
        Picasso.get().load(data[0].teamBadge).into(team_badge)
        team_name.text = data[0].teamName
        team_formed_year.text = data[0].teamFormedYear
        team_stadion.text = data[0].teamStadium

        teamName = data[0].teamName.toString()
    }

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teamId: String
    private lateinit var teamName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        teamId = intent.getStringExtra("ID")

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(teamId)

        val mAdapter = FavoritesViewPagerAdapter(supportFragmentManager)
        mAdapter.addFragment(OverviewFragment(), "overview")
        mAdapter.addFragment(NextMatchFragment(), "players")

        app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    collapsingToolbarLayout.title = "\t"
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = teamName
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title = "\t"
                    isShow = false
                }
            }
        })

        viewPager.adapter = mAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
