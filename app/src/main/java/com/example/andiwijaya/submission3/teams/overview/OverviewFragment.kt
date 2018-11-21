package com.example.andiwijaya.submission3.teams.overview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.model.Team
import com.example.andiwijaya.submission3.teams.detail.TeamDetailPresenter
import com.example.andiwijaya.submission3.teams.detail.TeamDetailView
import com.example.andiwijaya.submission3.util.gone
import com.example.andiwijaya.submission3.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_last_match.view.*
import kotlinx.android.synthetic.main.fragment_team_overview.*

class OverviewFragment : Fragment(), TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teamId: String

    override fun showLoading() {
        view?.progressBar?.visible()
    }

    override fun hideLoading() {
        view?.progressBar?.gone()
    }

    override fun showTeamDetail(data: List<Team>) {
        overviewTV.text = data[0].teamDescription
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_team_overview, container, false)

        view.swipeRefreshLayout?.setColorSchemeResources(
            R.color.colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail("133610")

        return view
    }

}