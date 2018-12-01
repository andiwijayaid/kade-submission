package com.example.andiwijaya.submission3.searchview

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.andiwijaya.submission3.R
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
import kotlinx.android.synthetic.main.activity_search_match.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity

class SearchMatchActivity : AppCompatActivity(), MatchesView {

    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchesPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var query: String

    override fun showLoading() {
        progressBar?.visible()
    }

    override fun hideLoading() {
        progressBar?.gone()
    }

    override fun getDataFromAPI() {
        if (checkInternetConnection(this)) {
            presenter.getEventList(query)
        } else {
            progressBar.gone()
            searchMatchLL.snackbar(R.string.check_connection).setDuration(10000).show()
        }
    }

    override fun showListMatch(data: List<Match>) {
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)

        val matchQuery = intent.getStringExtra("SEARCH_QUERY")

        setSupportActionBar(toolbar)
        toolbar.title = matchQuery
        toolbar.setNavigationOnClickListener {
            finish()
        }

        adapter = MainAdapter(
            matches,
            applicationContext,
            {
                startActivity<MatchDetailActivity>(
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
                startActivity(intent)
            })

        searchRV.adapter = adapter
        searchRV.layoutManager = LinearLayoutManager(applicationContext)
        val request = ApiRepository()
        val gson = Gson()

        presenter = MatchesPresenter(this, request, gson)
        query = matchQuery
        getDataFromAPI()
    }
}
