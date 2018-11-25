package com.example.andiwijaya.submission3.teams.detail.players.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.model.Player
import com.example.andiwijaya.submission3.model.Team
import com.example.andiwijaya.submission3.util.gone
import com.example.andiwijaya.submission3.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView {
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showPlayerDetail(data: List<Player>) {
        if (data[0].playerImage != null) {
            Picasso.get().load(data[0].playerImage).into(playerIV)
        } else {
            noPictTV.visible()
        }

        playerName = data[0].playerName.toString()
        playerNameTV.text = playerName
        playerPositionTV.text = data[0].playerPosition

        if (data[0].playerBodyWeight != null || data[0].playerHeight == "") {
            playerWeightTV.text = data[0].playerBodyWeight
        }

        if (data[0].playerHeight != null || data[0].playerHeight == "") {
            playerHeightTV.text = data[0].playerHeight
        }

        playerDescTV.text = data[0].playerDescription
    }

    override fun showBadge(data: List<Team>) {
        Picasso.get().load(data[0].teamBadge).into(teamBadgeIV)
    }

    private lateinit var teamId: String
    private lateinit var playerId: String
    private lateinit var playerName: String
    private lateinit var presenter: PlayerDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    profileTV.visible()
                    collapsingToolbarLayout.title = "\t"
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    profileTV.gone()
                    collapsingToolbarLayout.title = playerName
                    isShow = true
                } else if (isShow) {
                    profileTV.visible()
                    collapsingToolbarLayout.title = "\t"
                    isShow = false
                }
            }
        })

        playerId = intent.getStringExtra("PLAYER_ID")
        teamId = intent.getStringExtra("TEAM_ID")

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerDetailPresenter(this, request, gson)
        presenter.getPlayerDetail(playerId)
        presenter.getHomeBadge(teamId)

    }
}
