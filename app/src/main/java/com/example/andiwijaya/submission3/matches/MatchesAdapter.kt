package com.example.andiwijaya.submission3.matches

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
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
import android.util.Log
import com.example.andiwijaya.submission3.util.convertToMillis


class MainAdapter(
    private var matches: MutableList<Match>,
    private val context: Context?,
    private val listener: (Match) -> Unit,
    private val bellListener: (Match) -> Unit
) :
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

    override fun onBindViewHolder(p0: MatchViewHolder, p1: Int) {
        p0.bindItem(matches[p1], listener, bellListener)

        p0.bellIB.setOnClickListener {
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra(CalendarContract.Events.TITLE, matches[p1].eventName)
            intent.putExtra(CalendarContract.Events.DESCRIPTION, matches[p1].fileName)
            intent.putExtra(CalendarContract.Events.HAS_ALARM, 1)
            intent.putExtra(
                CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                convertToMillis(matches[p1].dateEvent!!, matches[p1].time!!)
            )
            intent.putExtra(
                CalendarContract.EXTRA_EVENT_END_TIME,
                convertToMillis(matches[p1].dateEvent!!, matches[p1].time!!) + 60 * 90 * 1000
            )
            intent.putExtra(CalendarContract.Events.ALL_DAY, false)
            context?.startActivity(intent)
        }
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

    fun bindItem(matches: Match, listener: (Match) -> Unit, bellListener: (Match) -> Unit) {
        homeScoreTV.text = matches.homeScore
        awayScoreTV.text = matches.awayScore
        homeNameTV.text = matches.homeTeam
        awayNameTV.text = matches.awayTeam

        if (matches.dateEvent != null) {
            tanggalTV.text = formatDate(matches.dateEvent!!)
        } else {
            tanggalTV.text = null
        }

        if (matches.time != null) {
            timeTV.text = formatTimeToGMT(matches.time.toString())
        } else {
            timeTV.text = null
        }

        matchLL.onClick { listener(matches) }
        bellIB.onClick { bellListener(matches) }

        // remove bell image button for last match
        if (matches.homeScore != null) {
            bellIB.gone()
        }
    }
}
