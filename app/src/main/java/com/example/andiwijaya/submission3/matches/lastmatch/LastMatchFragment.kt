package com.example.andiwijaya.submission3.matches.lastmatch

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
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
import com.example.andiwijaya.submission3.util.checkInternetConnection
import com.example.andiwijaya.submission3.util.convertToMillis
import com.example.andiwijaya.submission3.util.gone
import com.example.andiwijaya.submission3.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_last_match.*
import kotlinx.android.synthetic.main.fragment_last_match.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class LastMatchFragment : Fragment(), MatchesView {

    private lateinit var leagueName: String
    private lateinit var leagueId: String
    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchesPresenter
    private lateinit var adapter: MainAdapter

    override fun showLoading() {
        view?.progressBar?.visible()
    }

    override fun hideLoading() {
        view?.progressBar?.gone()
    }

    override fun getDataFromAPI() {
        if (checkInternetConnection(activity)) {
            presenter.getPastMatchList(leagueId)
        } else {
            progressBar?.gone()
            swipeRefreshLayout?.isRefreshing = false
            last_match_fragment?.snackbar(
                R.string.check_connection,
                R.string.refresh,
                {
                    getDataFromAPI()
                }
            )?.setDuration(10000)?.show()
        }
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

        adapter = MainAdapter(
            matches,
            context,
            {
                activity?.applicationContext?.startActivity<MatchDetailActivity>(
                    "FILE_NAME" to "${it.fileName}",
                    "ID" to "${it.matchId}"
                )
            },
            {
                val intent = Intent(Intent.ACTION_EDIT)
                intent.type = "vnd.android.cursor.item/event"
                intent.putExtra(CalendarContract.Events.TITLE, it.eventName)
                intent.putExtra(CalendarContract.Events.DESCRIPTION, it.fileName)
                intent.putExtra(CalendarContract.Events.HAS_ALARM, 1)
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, convertToMillis(it.dateEvent!!, it.time!!))
                intent.putExtra(
                    CalendarContract.EXTRA_EVENT_END_TIME,
                    convertToMillis(it.dateEvent!!, it.time!!) + 60 * 90 * 1000
                )
                intent.putExtra(CalendarContract.Events.ALL_DAY, false)
                activity?.applicationContext?.startActivity(intent)
            })

        view.lastMatchRV.adapter = adapter
        view.lastMatchRV.layoutManager = LinearLayoutManager(context)
        val request = ApiRepository()
        val gson = Gson()

        presenter = MatchesPresenter(this@LastMatchFragment, request, gson)
        leagueId = "4328"
        getDataFromAPI()

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        view.leagueSpinner.adapter = spinnerAdapter

        view.leagueSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = leagueSpinner.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> leagueId = "4328"
                    "English League Championship" -> leagueId = "4329"
                    "German Bundesliga" -> leagueId = "4331"
                    "Italian Serie A" -> leagueId = "4332"
                    "French Ligue 1" -> leagueId = "4334"
                    "Spanish La Liga" -> leagueId = "4335"
                }
                getDataFromAPI()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        view.swipeRefreshLayout?.onRefresh {
            getDataFromAPI()
        }

        return view
    }

}