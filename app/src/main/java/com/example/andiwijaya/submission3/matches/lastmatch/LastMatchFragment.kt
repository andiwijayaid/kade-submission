package com.example.andiwijaya.submission3.matches.lastmatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.R.color.colorAccent
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.matches.MainAdapter
import com.example.andiwijaya.submission3.matches.MatchesPresenter
import com.example.andiwijaya.submission3.matches.MatchesView
import com.example.andiwijaya.submission3.matches.detail.MatchDetailActivity
import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.util.gone
import com.example.andiwijaya.submission3.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_last_match.*
import kotlinx.android.synthetic.main.fragment_last_match.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class LastMatchFragment : Fragment(), MatchesView {

    private lateinit var leagueName: String
     var matches: MutableList<Match> = mutableListOf()
     lateinit var adapter: MainAdapter

    override fun showLoading() {
        view?.progressBar?.visible()
    }

    override fun hideLoading() {
        view?.progressBar?.gone()
    }

    override fun showListMatch(data: List<Match>) {
        swipeRefreshLayout?.isRefreshing = false
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_last_match, container, false)

        view.swipeRefreshLayout?.setColorSchemeResources(
            colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        adapter = MainAdapter(matches) {
            activity?.applicationContext?.startActivity<MatchDetailActivity>(
                "FILE_NAME" to "${it.fileName}",
                "ID" to "${it.matchId}"
            )
        }

        view.lastMatchRV.adapter = adapter
        view.lastMatchRV.layoutManager = LinearLayoutManager(context)
        val request = ApiRepository()
        val gson = Gson()

        val presenter = MatchesPresenter(this@LastMatchFragment, request, gson)
        presenter.getPastMatchList("4328")

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        view.leagueSpinner.adapter = spinnerAdapter

        view.leagueSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = leagueSpinner.selectedItem.toString()
                when(leagueName){
                    "English Premier League" -> presenter.getPastMatchList("4328")
                    "English League Championship" -> presenter.getPastMatchList("4329")
                    "German Bundesliga" -> presenter.getPastMatchList("4331")
                    "Italian Serie A" -> presenter.getPastMatchList("4332")
                    "French Ligue 1" -> presenter.getPastMatchList("4334")
                    "Spanish La Liga" -> presenter.getPastMatchList("4335")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        view.swipeRefreshLayout?.onRefresh {
            presenter.getPastMatchList("4328")
        }

        return view
    }

}