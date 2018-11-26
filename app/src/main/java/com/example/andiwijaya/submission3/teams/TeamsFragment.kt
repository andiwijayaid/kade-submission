package com.example.andiwijaya.submission3.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.R.array.league
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.home.HomeActivity
import com.example.andiwijaya.submission3.model.Team
import com.example.andiwijaya.submission3.teams.detail.TeamDetailActivity
import com.example.andiwijaya.submission3.util.gone
import com.example.andiwijaya.submission3.util.invisible
import com.example.andiwijaya.submission3.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_teams.*
import kotlinx.android.synthetic.main.fragment_teams.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh


class TeamsFragment : Fragment(), TeamsView {

    private lateinit var leagueName: String
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter

    override fun showLoading() {
        view?.progressBar?.visible()
    }

    override fun hideLoading() {
        view?.progressBar?.invisible()
    }

    override fun showListTeam(data: List<Team>) {
        swipeRefreshLayout?.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_teams, container, false)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(view.toolbar)

        view.swipeRefreshLayout?.setColorSchemeResources(
            R.color.colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        adapter = TeamsAdapter(teams) {
            activity?.applicationContext?.startActivity<TeamDetailActivity>(
                "ID" to it.teamId
            )
        }

        view.teamsRV.adapter = adapter
        view.teamsRV.layoutManager = LinearLayoutManager(context)

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        view.leagueSpinner.adapter = spinnerAdapter
        view.leagueSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = leagueSpinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        view.swipeRefreshLayout.onRefresh {
            presenter.getTeamList(leagueName)
        }

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.search_menu, menu)
        val item = menu?.findItem(R.id.search)
        val searchView = SearchView((context as HomeActivity).getSupportActionBar()?.getThemedContext())
        MenuItemCompat.setActionView(item, searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                leagueSpinner.gone()
                presenter.getTeamByNameList(p0!!)
                return false
            }
        })

        searchView.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                leagueSpinner.visible()
                return true
            }
        })
    }
}