package com.example.andiwijaya.submission3.matches

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.util.formatDate
import com.example.andiwijaya.submission3.util.formatTimeToGMT
import com.example.andiwijaya.submission3.util.gone
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainAdapter(private val matches: MutableList<Match>, private val listener: (Match) -> Unit) :
    RecyclerView.Adapter<MatchViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MatchViewHolder {
        return MatchViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.item_match,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int = matches.size

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(p0: MatchViewHolder, p1: Int) {
        p0.bindItem(matches[p1], listener)
    }

}

class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val homeNameTV: TextView = view.findViewById(R.id.homeNameTV)
    val homeScoreTV: TextView = view.findViewById(R.id.homeScoreTV)
    val awayNameTV: TextView = view.findViewById(R.id.awayNameTV)
    val awayScoreTV: TextView = view.findViewById(R.id.awayScoreTV)
    val tanggalTV: TextView = view.findViewById(R.id.dateTV)
    val timeTV: TextView = view.findViewById(R.id.timeTV)
    val matchLL: RelativeLayout = view.findViewById(R.id.matchLL)
    val bellIB: ImageButton = view.findViewById(R.id.bellIB)

    fun bindItem(matches: Match, listener: (Match) -> Unit) {
        homeNameTV.text = matches.homeTeam
        awayNameTV.text = matches.awayTeam
        homeScoreTV.text = matches.homeScore
        awayScoreTV.text = matches.awayScore
        tanggalTV.text = formatDate(matches.date!!)
        timeTV.text = formatTimeToGMT(matches.time!!)

        matchLL.onClick { listener(matches) }

        // remove bell image button for last match
        if (matches.homeScore != null) {
            bellIB.gone()
        }
    }
}
