package com.example.andiwijaya.submission3.fragment.nextmatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.matches.MainAdapter
import com.example.andiwijaya.submission3.matches.MatchesPresenter
import com.example.andiwijaya.submission3.matches.MatchesView
import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.util.invisible
import com.example.andiwijaya.submission3.util.visible
import com.google.gson.Gson
import com.example.andiwijaya.submission3.R.color.colorAccent
import kotlinx.android.synthetic.main.next_match_layout.*
import kotlinx.android.synthetic.main.next_match_layout.view.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class NextMatchFragment : Fragment(), MatchesView {

    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var adapter: MainAdapter

    override fun showLoading() {
        view?.progressBar?.visible()
    }

    override fun hideLoading() {
        view?.progressBar?.invisible()
    }

    override fun showListMatch(data: List<Match>) {
        swipeRefreshLayout.isRefreshing = false
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.next_match_layout, container, false)

        view.swipeRefreshLayout.setColorSchemeResources(
            colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        adapter = MainAdapter(matches, ctx)
        view.nextMatchRV.layoutManager = LinearLayoutManager(ctx)
        view.nextMatchRV.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()

        val presenter = MatchesPresenter(this@NextMatchFragment, request, gson)
        presenter.getNextMatchList("4328")

        view.swipeRefreshLayout.onRefresh {
            presenter.getNextMatchList("4328")
        }

        return view
    }

}