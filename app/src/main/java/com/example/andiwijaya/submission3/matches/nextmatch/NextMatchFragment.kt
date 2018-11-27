package com.example.andiwijaya.submission3.matches.nextmatch

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
import kotlinx.android.synthetic.main.fragment_next_match.*
import kotlinx.android.synthetic.main.fragment_next_match.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class NextMatchFragment : Fragment(), MatchesView {

    private lateinit var leagueName: String
    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var adapter: MainAdapter

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
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)

        view.swipeRefreshLayout?.setColorSchemeResources(
            colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        adapter = MainAdapter(matches, context) {
            context?.applicationContext?.startActivity<MatchDetailActivity>(
                "FILE_NAME" to "${it.fileName}",
                "ID" to "${it.matchId}"
            )
        }

        view.nextMatchRV.layoutManager = LinearLayoutManager(context)
        view.nextMatchRV.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()

        val presenter = MatchesPresenter(this@NextMatchFragment, request, gson)
        presenter.getNextMatchList("4328")

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        view.leagueSpinner.adapter = spinnerAdapter
        view.leagueSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = leagueSpinner.selectedItem.toString()
                when(leagueName){
                    "English Premier League" -> presenter.getNextMatchList("4328")
                    "English League Championship" -> presenter.getNextMatchList("4329")
                    "German Bundesliga" -> presenter.getNextMatchList("4331")
                    "Italian Serie A" -> presenter.getNextMatchList("4332")
                    "French Ligue 1" -> presenter.getNextMatchList("4334")
                    "Spanish La Liga" -> presenter.getNextMatchList("4335")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        view.swipeRefreshLayout?.onRefresh {
            presenter.getNextMatchList("4328")
        }

        return view
    }

}