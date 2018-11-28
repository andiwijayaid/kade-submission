package com.example.andiwijaya.submission3.matches.detail

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.R.drawable.ic_starred
import com.example.andiwijaya.submission3.R.drawable.ic_unstarred
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.db.database
import com.example.andiwijaya.submission3.model.FavoriteMatch
import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.model.Team
import com.example.andiwijaya.submission3.util.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {
    override fun showHomeBadge(data: List<Team>) {
        Picasso.get().load(data[0].teamBadge).into(homeBadgeIV)

    }

    override fun showAwayBadge(data: List<Team>) {
        Picasso.get().load(data[0].teamBadge).into(awayBadgeIV)
    }

    private lateinit var presenter: MatchDetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var fileName: String
    private lateinit var id: String

    private lateinit var match: Match

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        fileName = intent.getStringExtra("FILE_NAME")
        id = intent.getStringExtra("ID")

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchDetailPresenter(this, request, gson)
        presenter.getMatchDetail(fileName)

        swipeRefreshLayout.onRefresh {
            noDataTV.invisible()
            presenter.getMatchDetail(fileName)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteMatch.TABLE_MATCH_FAVORITE)
                .whereArgs(
                    "(MATCH_ID = {id})",
                    "id" to id
                )
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        detailLL.gone()
        progressBar.visible()
    }

    override fun hideLoading() {
        detailLL.visible()
        progressBar.invisible()
    }


    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun showMatchDetail(data: List<Match>) {

        match = Match(
            matchId = data[0].matchId,
            fileName = data[0].fileName,
            dateEvent = data[0].dateEvent,
            homeTeam = data[0].homeTeam,
            awayTeam = data[0].awayTeam,
            homeScore = data[0].homeScore,
            awayScore = data[0].awayScore
        )

        swipeRefreshLayout.isRefreshing = false

        presenter.getHomeBadge(data[0].homeTeamId!!)
        presenter.getAwayBadge(data[0].awayTeamId!!)

        homeNameTV.text = data[0].homeTeam
        awayNameTV.text = data[0].awayTeam

        dateTV.text = formatDate(data[0].dateEvent!!)
        timeTV.text = formatTimeToGMT(data[0].time!!)

        if (data[0].homeScore.isNullOrBlank()) {
            noDataTV.visible()
            detailSV.invisible()
        }

        data[0].homeGoalDetails = replaceSemicolonWithNewRow(data[0].homeGoalDetails!!)
        data[0].awayGoalDetails = replaceSemicolonWithNewRow(data[0].awayGoalDetails!!)

        data[0].homeLineupDefense = replaceSemicolonWithNewRow(data[0].homeLineupDefense!!)
        data[0].awayLineupDefense = replaceSemicolonWithNewRow(data[0].awayLineupDefense!!)

        data[0].homeLineupMidfield = replaceSemicolonWithNewRow(data[0].homeLineupMidfield!!)
        data[0].awayLineupMidfield = replaceSemicolonWithNewRow(data[0].awayLineupMidfield!!)

        data[0].homeLineupForward = replaceSemicolonWithNewRow(data[0].homeLineupForward!!)
        data[0].awayLineupForward = replaceSemicolonWithNewRow(data[0].awayLineupForward!!)

        data[0].homeLineupSubstitutes = replaceSemicolonWithNewRow(data[0].homeLineupSubstitutes!!)
        data[0].awayLineupSubstitutes = replaceSemicolonWithNewRow(data[0].awayLineupSubstitutes!!)

        homeScoreTV.text = data[0].homeScore
        awayScoreTV.text = data[0].awayScore

        homeGoalDetailTV.text = data[0].homeGoalDetails
        awayGoalDetailTV.text = data[0].awayGoalDetails

        homeShotTV.text = data[0].homeShots
        awayShotTV.text = data[0].awayShots

        homeGKTV.text = data[0].homeLineupGoalkeeper
        awayGKTV.text = data[0].awayLineupGoalkeeper

        homeDefenseTV.text = data[0].homeLineupDefense
        awayDefenseTV.text = data[0].awayLineupDefense

        homeMidfieldTV.text = data[0].homeLineupMidfield
        awayMidfieldTV.text = data[0].awayLineupMidfield

        homeForwardTV.text = data[0].homeLineupForward
        awayForwardTV.text = data[0].awayLineupForward

        homeSubsTV.text = data[0].homeLineupSubstitutes
        awaySubsTV.text = data[0].awayLineupSubstitutes
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                checkFavoriteStat()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkFavoriteStat() {
        if (isFavorite)
            removeFromFavorite()
        else
            addToFavorite()

        isFavorite = !isFavorite
        setFavorite()
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    FavoriteMatch.TABLE_MATCH_FAVORITE,
                    FavoriteMatch.MATCH_ID to match.matchId,
                    FavoriteMatch.MATCH_FILE_NAME to match.fileName,
                    FavoriteMatch.MATCH_DATE to match.dateEvent,
                    FavoriteMatch.HOME_NAME to match.homeTeam,
                    FavoriteMatch.AWAY_NAME to match.awayTeam,
                    FavoriteMatch.HOME_SCORE to match.homeScore,
                    FavoriteMatch.AWAY_SCORE to match.awayScore
                )
            }
            detailRL.snackbar("Ditambahkan ke favorite", "undo") {
                checkFavoriteStat()
                removeFromFavorite()
            }
        } catch (e: SQLiteConstraintException) {
            Log.d("e", e.toString())
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    FavoriteMatch.TABLE_MATCH_FAVORITE, "(MATCH_ID = {id})",
                    "id" to id
                )
            }
            detailRL.snackbar("Dihapus dari favorite", "undo") {
                checkFavoriteStat()
                addToFavorite()
            }
        } catch (e: SQLiteConstraintException) {
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_starred)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_unstarred)
    }

}
