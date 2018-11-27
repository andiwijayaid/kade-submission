package com.example.andiwijaya.submission3.searchview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.api.ApiRepository
import com.example.andiwijaya.submission3.matches.MainAdapter
import com.example.andiwijaya.submission3.matches.MatchesPresenter
import com.example.andiwijaya.submission3.matches.MatchesView
import com.example.andiwijaya.submission3.matches.detail.MatchDetailActivity
import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.util.gone
import com.example.andiwijaya.submission3.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_match.*
import org.jetbrains.anko.startActivity

class SearchMatchActivity : AppCompatActivity(), MatchesView {

    var matches: MutableList<Match> = mutableListOf()
    lateinit var adapter: MainAdapter

    override fun showLoading() {
        progressBar?.visible()
    }

    override fun hideLoading() {
        progressBar?.gone()
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

        adapter = MainAdapter(matches) {
            applicationContext?.startActivity<MatchDetailActivity>(
                "FILE_NAME" to "${it.fileName}",
                "ID" to "${it.matchId}"
            )
        }

        searchRV.adapter = adapter
        searchRV.layoutManager = LinearLayoutManager(applicationContext)
        val request = ApiRepository()
        val gson = Gson()

        val presenter = MatchesPresenter(this, request, gson)
        presenter.getEventList(matchQuery)
        Log.d("a", matchQuery)
    }

}
