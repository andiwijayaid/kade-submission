package com.example.andiwijaya.submission3.detail

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
import com.example.andiwijaya.submission3.model.Favorite
import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.util.gone
import com.example.andiwijaya.submission3.util.invisible
import com.example.andiwijaya.submission3.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {

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
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(MATCH_ID = {id})",
                    "id" to id
                )
            val favorite = result.parseList(classParser<Favorite>())
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
            data[0].homeTeam,
            data[0].awayTeam,
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

        when (data[0].homeTeam) {
            "Arsenal" -> homeBadgeIV.setImageResource(R.drawable.arsenal)
            "Bournemouth" -> homeBadgeIV.setImageResource(R.drawable.bournemouth)
            "Brighton" -> homeBadgeIV.setImageResource(R.drawable.brighton)
            "Burnley" -> homeBadgeIV.setImageResource(R.drawable.burnley)
            "Cardiff" -> homeBadgeIV.setImageResource(R.drawable.cardiff)
            "Chelsea" -> homeBadgeIV.setImageResource(R.drawable.chelsea)
            "Crystal Palace" -> homeBadgeIV.setImageResource(R.drawable.crystal_palace)
            "Everton" -> homeBadgeIV.setImageResource(R.drawable.everton)
            "Fulham" -> homeBadgeIV.setImageResource(R.drawable.fulham)
            "Huddersfield Town" -> homeBadgeIV.setImageResource(R.drawable.huddersfield_town)
            "Leicester" -> homeBadgeIV.setImageResource(R.drawable.leicester)
            "Liverpool" -> homeBadgeIV.setImageResource(R.drawable.liverpool)
            "Man City" -> homeBadgeIV.setImageResource(R.drawable.man_city)
            "Man United" -> homeBadgeIV.setImageResource(R.drawable.man_united)
            "Newcastle" -> homeBadgeIV.setImageResource(R.drawable.newcastle)
            "Southampton" -> homeBadgeIV.setImageResource(R.drawable.southampton)
            "Tottenham" -> homeBadgeIV.setImageResource(R.drawable.tottenham)
            "Watford" -> homeBadgeIV.setImageResource(R.drawable.watford)
            "West Ham" -> homeBadgeIV.setImageResource(R.drawable.westham)
            "Wolves" -> homeBadgeIV.setImageResource(R.drawable.wolves)
        }

        when (data[0].awayTeam) {
            "Arsenal" -> awayBadgeIV.setImageResource(R.drawable.arsenal)
            "Bournemouth" -> awayBadgeIV.setImageResource(R.drawable.bournemouth)
            "Brighton" -> awayBadgeIV.setImageResource(R.drawable.brighton)
            "Burnley" -> awayBadgeIV.setImageResource(R.drawable.burnley)
            "Cardiff" -> awayBadgeIV.setImageResource(R.drawable.cardiff)
            "Chelsea" -> awayBadgeIV.setImageResource(R.drawable.chelsea)
            "Crystal Palace" -> awayBadgeIV.setImageResource(R.drawable.crystal_palace)
            "Everton" -> awayBadgeIV.setImageResource(R.drawable.everton)
            "Fulham" -> awayBadgeIV.setImageResource(R.drawable.fulham)
            "Huddersfield Town" -> awayBadgeIV.setImageResource(R.drawable.huddersfield_town)
            "Leicester" -> awayBadgeIV.setImageResource(R.drawable.leicester)
            "Liverpool" -> awayBadgeIV.setImageResource(R.drawable.liverpool)
            "Man City" -> awayBadgeIV.setImageResource(R.drawable.man_city)
            "Man United" -> awayBadgeIV.setImageResource(R.drawable.man_united)
            "Newcastle" -> awayBadgeIV.setImageResource(R.drawable.newcastle)
            "Southampton" -> awayBadgeIV.setImageResource(R.drawable.southampton)
            "Tottenham" -> awayBadgeIV.setImageResource(R.drawable.tottenham)
            "Watford" -> awayBadgeIV.setImageResource(R.drawable.watford)
            "West Ham" -> awayBadgeIV.setImageResource(R.drawable.westham)
            "Wolves" -> awayBadgeIV.setImageResource(R.drawable.wolves)
        }
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
                    Favorite.TABLE_FAVORITE,
                    Favorite.MATCH_ID to match.matchId,
                    Favorite.MATCH_FILE_NAME to match.fileName,
                    Favorite.MATCH_DATE to match.date,
                    Favorite.HOME_NAME to match.homeTeam,
                    Favorite.AWAY_NAME to match.awayTeam,
                    Favorite.HOME_SCORE to match.homeScore,
                    Favorite.AWAY_SCORE to match.awayScore
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
                    Favorite.TABLE_FAVORITE, "(MATCH_ID = {id})",
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
