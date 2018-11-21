package com.example.andiwijaya.submission3.matches

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.model.Match
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

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
    val matchLL: LinearLayout = view.findViewById(R.id.matchLL)

    fun bindItem(matches: Match, listener: (Match) -> Unit) {
        homeNameTV.text = matches.homeTeam
        awayNameTV.text = matches.awayTeam
        homeScoreTV.text = matches.homeScore
        awayScoreTV.text = matches.awayScore
        tanggalTV.text = formatDate(matches.date!!)
        timeTV.text = formatTimeToGMT(matches.time!!)

        matchLL.onClick { listener(matches) }
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: String): String {
        val df = SimpleDateFormat("dd/MM/yyyy")
        val result = df.parse(date)
        val dayFormat = SimpleDateFormat("E")
        val namaHari = dayFormat.format(result)

        val newDateFormat = SimpleDateFormat("dd MMM yy")
        val newDate = newDateFormat.format(result)

        return "$namaHari, $newDate"
    }

    @SuppressLint("SimpleDateFormat")
    fun formatTimeToGMT(time: String): String {

        val df = SimpleDateFormat("HH:mm")
        val formattedTime = df.parse(time)
        df.timeZone = TimeZone.getTimeZone("GMT+07")

        return df.format(formattedTime)
    }
}
