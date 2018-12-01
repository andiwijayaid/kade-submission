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

    private lateinit var presenter: MatchDetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var fileName: String
    private lateinit var homeId: String
    private lateinit var awayId: String
    private lateinit var id: String
    private lateinit var match: Match

    // save state if data is downloaded from API or not
    private var isDataDownloaded: Boolean = false

    override fun getAwayBadgeFromAPI() {
        if (checkInternetConnection(this)) {
            presenter.getAwayBadge(awayId)
        } else {
            detailLL.invisible()
            progressBar.gone()
            swipeRefreshLayout.isRefreshing = false
            detailRL.snackbar(
                R.string.check_connection,
                R.string.refresh,
                {
                    getAwayBadgeFromAPI()
                }
            ).setDuration(10000).show()
        }
    }

    override fun getHomeBadgeFromAPI() {
        if (checkInternetConnection(this)) {
            presenter.getHomeBadge(homeId)
        } else {
            detailLL.invisible()
            progressBar.gone()
            swipeRefreshLayout.isRefreshing = false
            detailRL.snackbar(
                R.string.check_connection,
                R.string.refresh,
                {
                    getHomeBadgeFromAPI()
                }
            ).setDuration(10000).show()
        }
    }

    override fun getMatchDetailFromAPI() {
        if (checkInternetConnection(this)) {
            presenter.getMatchDetail(fileName)
            isDataDownloaded = true
        } else {
            detailLL.invisible()
            progressBar.gone()
            swipeRefreshLayout.isRefreshing = false
            detailRL.snackbar(
                R.string.check_connection,
                R.string.refresh,
                {
                    getMatchDetailFromAPI()
                }
            ).setDuration(10000).show()
            isDataDownloaded = false
        }
    }

    override fun showHomeBadge(data: List<Team>) {
        Picasso.get().load(data[0].teamBadge).into(homeBadgeIV)

    }

    override fun showAwayBadge(data: List<Team>) {
        Picasso.get().load(data[0].teamBadge).into(awayBadgeIV)
    }

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
        getMatchDetailFromAPI()

        swipeRefreshLayout.onRefresh {
            noDataTV.invisible()
            getMatchDetailFromAPI()
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

        homeId = data[0].homeTeamId!!
        getHomeBadgeFromAPI()
        awayId = data[0].awayTeamId!!
        getAwayBadgeFromAPI()

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

        homeNameTV.text = data[0].homeTeam
        awayNameTV.text = data[0].awayTeam

        val dateTime = formatDateTimeToGMT(data[0].dateEvent!!, data[0].time!!)
        dateTV.text = getDateOnly(dateTime)
        timeTV.text = getTimeOnly(dateTime)

        homeScoreTV.text = data[0].homeScore
        awayScoreTV.text = data[0].awayScore

        if (data[0].homeScore.isNullOrBlank()) {
            noDataTV.visible()
            detailSV.invisible()
        }

        homeShotTV.text = data[0].homeShots
        awayShotTV.text = data[0].awayShots

        homeGKTV.text = data[0].homeLineupGoalkeeper
        awayGKTV.text = data[0].awayLineupGoalkeeper

        data[0].homeGoalDetails = replaceSemicolonWithNewRow(data[0].homeGoalDetails!!)
        data[0].awayGoalDetails = replaceSemicolonWithNewRow(data[0].awayGoalDetails!!)
        homeGoalDetailTV.text = data[0].homeGoalDetails
        awayGoalDetailTV.text = data[0].awayGoalDetails

        data[0].homeLineupDefense = replaceSemicolonWithNewRow(data[0].homeLineupDefense!!)
        data[0].awayLineupDefense = replaceSemicolonWithNewRow(data[0].awayLineupDefense!!)
        homeDefenseTV.text = data[0].homeLineupDefense
        awayDefenseTV.text = data[0].awayLineupDefense

        data[0].homeLineupMidfield = replaceSemicolonWithNewRow(data[0].homeLineupMidfield!!)
        data[0].awayLineupMidfield = replaceSemicolonWithNewRow(data[0].awayLineupMidfield!!)
        homeMidfieldTV.text = data[0].homeLineupMidfield
        awayMidfieldTV.text = data[0].awayLineupMidfield

        data[0].homeLineupForward = replaceSemicolonWithNewRow(data[0].homeLineupForward!!)
        data[0].awayLineupForward = replaceSemicolonWithNewRow(data[0].awayLineupForward!!)
        homeForwardTV.text = data[0].homeLineupForward
        awayForwardTV.text = data[0].awayLineupForward

        data[0].homeLineupSubstitutes = replaceSemicolonWithNewRow(data[0].homeLineupSubstitutes!!)
        data[0].awayLineupSubstitutes = replaceSemicolonWithNewRow(data[0].awayLineupSubstitutes!!)
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
                // prevent user to add match to favorite when no connection/data is not downloaded
                if (isDataDownloaded) {
                    checkFavoriteStat()
                } else {
                    toast(R.string.check_connection)
                }
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
            detailRL.snackbar(
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
                    FavoriteMatch.TABLE_MATCH_FAVORITE, "(MATCH_ID = {id})",
                    "id" to id
                )
            }
            detailRL.snackbar(
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
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_starred)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_unstarred)
    }

}
