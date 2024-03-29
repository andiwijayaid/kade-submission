package com.example.andiwijaya.submission3.teams.detail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.db.database
import com.example.andiwijaya.submission3.favorites.FavoritesViewPagerAdapter
import com.example.andiwijaya.submission3.model.FavoriteTeam
import com.example.andiwijaya.submission3.model.Team
import com.example.andiwijaya.submission3.teams.detail.overview.OverviewFragment
import com.example.andiwijaya.submission3.teams.detail.players.PlayersFragment
import com.example.andiwijaya.submission3.util.checkInternetConnection
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast


class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var teamName: String? = null
    private lateinit var teamId: String
    private lateinit var team: Team

    // save state if data is downloaded from API or not
    private var isDataDownloaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        teamId = intent.getStringExtra("ID")

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        getDataFromAPI()

        val mAdapter = FavoritesViewPagerAdapter(supportFragmentManager)
        mAdapter.addFragment(OverviewFragment(), "overview")
        mAdapter.addFragment(PlayersFragment(), "players")

        app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    collapsingToolbarLayout.title = "\t"
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = teamName
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title = "\t"
                    isShow = false
                }
            }
        })

        viewPager.adapter = mAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_TEAM_FAVORITE)
                .whereArgs(
                    "(TEAM_ID = {id})",
                    "id" to teamId
                )
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun getDataFromAPI() {
        if (checkInternetConnection(this)) {
            presenter.getTeamDetail(teamId)
            isDataDownloaded = true
        } else {
            toast(R.string.check_connection)
            isDataDownloaded = false
        }
    }

    override fun showTeamDetail(data: List<Team>) {
        team = Team(
            data[0].teamId,
            data[0].teamName,
            data[0].teamBadge
        )
        Picasso.get().load(data[0].teamDetailBackground).into(teamBackgroundIV)
        Picasso.get().load(data[0].teamBadge).into(team_badge)
        team_name.text = data[0].teamName
        team_formed_year.text = data[0].teamFormedYear
        team_stadion.text = data[0].teamStadium

        teamName = data[0].teamName.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_to_favorite -> {
                // prevent user to add match to favorite when no connection/data is not downloaded
                if (isDataDownloaded) {
                    checkFavoriteStat()
                } else {
                    toast(R.string.check_connection)
                }
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun checkFavoriteStat() {
        if (isFavorite) {
            removeFromFavorite()
        } else {
            addToFavorite()
        }

        isFavorite = !isFavorite
        setFavorite()
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    FavoriteTeam.TABLE_TEAM_FAVORITE,
                    FavoriteTeam.TEAM_ID to team.teamId,
                    FavoriteTeam.TEAM_NAME to team.teamName,
                    FavoriteTeam.TEAM_BADGE to team.teamBadge
                )
            }
            coordinator.snackbar(
                R.string.added_to_favorite,
                R.string.undo,
                {
                    checkFavoriteStat()
                    removeFromFavorite()
                }
            )
        } catch (e: SQLiteConstraintException) {
            Log.d("e", e.toString())
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    FavoriteTeam.TABLE_TEAM_FAVORITE, "(TEAM_ID = {id})",
                    "id" to teamId
                )
            }
            coordinator.snackbar(
                R.string.deleted_from_favorite,
                R.string.undo,
                {
                    checkFavoriteStat()
                    addToFavorite()
                }
            )
        } catch (e: SQLiteConstraintException) {
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_starred)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_unstarred)
    }

}
