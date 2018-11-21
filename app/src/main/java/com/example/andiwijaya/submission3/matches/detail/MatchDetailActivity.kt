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
import com.example.andiwijaya.submission3.R.drawable.ic_add_to_favorites
import com.example.andiwijaya.submission3.R.drawable.ic_added_to_favorites
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.db.database
import com.example.andiwijaya.submission3.model.FavoriteMatch
import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.model.Team
import com.example.andiwijaya.submission3.util.gone
import com.example.andiwijaya.submission3.util.invisible
import com.example.andiwijaya.submission3.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.item_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat

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
            val result = select(FavoriteMatch.TABLE_FAVORITE)
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
        progressBar.gone()
    }


    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun showMatchDetail(data: List<Match>) {
        match = Match(
            data[0].date,
            data[0].time,
            data[0].homeTeam,
            data[0].homeTeamId,
            data[0].awayTeam,
            data[0].awayTeamId,
            data[0].homeScore,
            data[0].awayScore,
            data[0].homeGoalDetails,
            data[0].awayGoalDetails,
            data[0].homeShots,
            data[0].awayShots,
            data[0].homeLineupGoalkeeper,
            data[0].homeLineupDefense,
            data[0].homeLineupMidfield,
            data[0].homeLineupForward,
            data[0].homeLineupSubstitutes,
            data[0].awayLineupGoalkeeper,
            data[0].awayLineupDefense,
            data[0].awayLineupMidfield,
            data[0].awayLineupForward,
            data[0].awayLineupSubstitutes,
            data[0].fileName,
            data[0].matchId
        )

        if (data[0].homeGoalDetails == null) {
            noDataTV.visible()
            detailSV.invisible()
        }

        swipeRefreshLayout.isRefreshing = false

        data[0].homeGoalDetails = match.homeGoalDetails?.replace(";", "\n")
        data[0].awayGoalDetails = match.awayGoalDetails?.replace(";", "\n")

        data[0].homeLineupDefense = match.homeLineupDefense?.replace(";", "\n")
        data[0].awayLineupDefense = match.awayLineupDefense?.replace(";", "\n")

        data[0].homeLineupMidfield = match.homeLineupMidfield?.replace(";", "\n")
        data[0].awayLineupMidfield = match.awayLineupMidfield?.replace(";", "\n")

        data[0].homeLineupForward = match.homeLineupForward?.replace(";", "\n")
        data[0].awayLineupForward = match.awayLineupForward?.replace(";", "\n")

        data[0].homeLineupSubstitutes = match.homeLineupSubstitutes?.replace(";", "\n")
        data[0].awayLineupSubstitutes = match.awayLineupSubstitutes?.replace(";", "\n")

        homeNameTV.text = data[0].homeTeam
        awayNameTV.text = data[0].awayTeam

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

        val df = SimpleDateFormat("dd/MM/yyyy")
        val result = df.parse(data[0].date)
        val dayFormat = SimpleDateFormat("E")
        val namaHari = dayFormat.format(result)

        val newDateFormat = SimpleDateFormat("dd MMM yy")
        val newDate = newDateFormat.format(result)

        dateTV.text = "$namaHari, $newDate"

        presenter.getHomeBadge(data[0].homeTeamId!!)
        presenter.getAwayBadge(data[0].awayTeamId!!)

        Log.d("G", data[0].homeTeamId!!)
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
                    FavoriteMatch.TABLE_FAVORITE,
                    FavoriteMatch.MATCH_ID to match.matchId,
                    FavoriteMatch.MATCH_FILE_NAME to match.fileName,
                    FavoriteMatch.MATCH_DATE to match.date,
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
                    FavoriteMatch.TABLE_FAVORITE, "(MATCH_ID = {id})",
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
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}
