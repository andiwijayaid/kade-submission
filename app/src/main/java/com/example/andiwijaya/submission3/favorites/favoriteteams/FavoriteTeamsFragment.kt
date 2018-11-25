package com.example.andiwijaya.submission3.favorites.favoriteteams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.db.database
import com.example.andiwijaya.submission3.model.FavoriteTeam
import com.example.andiwijaya.submission3.teams.detail.TeamDetailActivity
import kotlinx.android.synthetic.main.fragment_favorite_teams.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavoriteTeamsFragment: Fragment() {

    private var favoriteTeams: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamsAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_teams, container, false)

        swipeRefresh = view.findViewById(R.id.swipeRefreshLayout)
        adapter = FavoriteTeamsAdapter(favoriteTeams) {
            startActivity<TeamDetailActivity>(
                "ID" to it.teamId
            )
        }

        view.favoriteTeamRV.layoutManager = LinearLayoutManager(context)
        view.favoriteTeamRV.adapter = adapter
        showFavorite()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipeRefresh.onRefresh {
            favoriteTeams.clear()
            showFavorite()
        }
    }

    private fun showFavorite() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_TEAM_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favoriteTeams.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}