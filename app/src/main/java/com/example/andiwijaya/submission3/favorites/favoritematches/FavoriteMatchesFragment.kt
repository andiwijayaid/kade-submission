package com.example.andiwijaya.submission3.favorites.favoritematches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.db.database
import com.example.andiwijaya.submission3.matches.detail.MatchDetailActivity
import com.example.andiwijaya.submission3.model.FavoriteMatch
import kotlinx.android.synthetic.main.fragment_favorite_matches.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavoriteMatchesFragment : Fragment() {

    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavoriteMatchesAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_matches, container, false)

        swipeRefresh = view.findViewById(R.id.swipeRefreshLayout)
        adapter = FavoriteMatchesAdapter(favoriteMatches) {
            startActivity<MatchDetailActivity>(
                "FILE_NAME" to it.fileName,
                "ID" to it.matchId
            )
        }

        view.favoriteMatchRV.layoutManager = LinearLayoutManager(context)
        view.favoriteMatchRV.adapter = adapter
        showFavorite()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipeRefresh.onRefresh {
            favoriteMatches.clear()
            showFavorite()
        }
    }

    private fun showFavorite() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_MATCH_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favoriteMatches.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}