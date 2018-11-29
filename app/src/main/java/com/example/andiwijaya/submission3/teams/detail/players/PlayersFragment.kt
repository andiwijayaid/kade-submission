package com.example.andiwijaya.submission3.teams.detail.players

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.model.Player
import com.example.andiwijaya.submission3.teams.detail.players.detail.PlayerDetailActivity
import com.example.andiwijaya.submission3.util.checkInternetConnection
import com.example.andiwijaya.submission3.util.gone
import com.example.andiwijaya.submission3.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team_overview.*
import kotlinx.android.synthetic.main.fragment_team_players.view.*
import org.jetbrains.anko.startActivity

class PlayersFragment : Fragment(), PlayerView {

    private lateinit var presenter: PlayerPresenter
    private var players: MutableList<Player> = mutableListOf()
    private lateinit var teamId: String
    private lateinit var adapter: PlayerAdapter

    override fun showLoading() {
        view?.progressBar?.visible()
    }

    override fun hideLoading() {
        view?.progressBar?.gone()
    }

    override fun getDataFromAPI() {
        if (checkInternetConnection(activity)) {
            presenter.getPlayers(teamId)
        }
    }

    override fun showPlayerDetail(data: List<Player>) {
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_team_players, container, false)
        teamId = activity?.intent?.getStringExtra("ID").toString()

        adapter = PlayerAdapter(players) {
            context?.applicationContext?.startActivity<PlayerDetailActivity>(
                "PLAYER_ID" to it.playerId,
                "TEAM_ID" to it.teamId
            )
        }

        view.playersRV.layoutManager = GridLayoutManager(context, 2)
        view.playersRV.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)
        getDataFromAPI()

        return view
    }

}