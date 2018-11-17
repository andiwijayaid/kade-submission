package com.example.andiwijaya.submission3.fragment.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.model.Favorite
import com.example.andiwijaya.submission3.db.database
import kotlinx.android.synthetic.main.favorite_match_layout.view.*
import kotlinx.android.synthetic.main.last_match_layout.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteMatchesFragment : Fragment() {

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteMatchesAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.favorite_match_layout, container, false)

        swipeRefresh = view.findViewById(R.id.swipeRefreshLayout)
        adapter = FavoriteMatchesAdapter(favorites, view.context)
        view.favoriteMatchRV.layoutManager = LinearLayoutManager(context)
        view.favoriteMatchRV.adapter = adapter
        showFavorite()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}