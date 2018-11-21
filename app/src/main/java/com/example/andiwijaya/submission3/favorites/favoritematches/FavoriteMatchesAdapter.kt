package com.example.andiwijaya.submission3.favorites.favoritematches

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.model.FavoriteMatch
import org.jetbrains.anko.sdk27.coroutines.onClick

class FavoriteMatchesAdapter(private val favoriteMatch: List<FavoriteMatch>, private val context: Context, private val listener: (FavoriteMatch) -> Unit) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_match,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favoriteMatch[position], listener)
    }

    override fun getItemCount(): Int = favoriteMatch.size

}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val homeNameTV: TextView = view.findViewById(R.id.homeNameTV)
    val homeScoreTV: TextView = view.findViewById(R.id.homeScoreTV)
    val awayNameTV: TextView = view.findViewById(R.id.awayNameTV)
    val awayScoreTV: TextView = view.findViewById(R.id.awayScoreTV)
    val tanggalTV: TextView = view.findViewById(R.id.dateTV)
    val matchLL: RelativeLayout = view.findViewById(R.id.matchLL)

    fun bindItem(favorites: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {
        homeNameTV.text = favorites.homeName
        awayNameTV.text = favorites.awayName
        homeScoreTV.text = favorites.homeScore
        awayScoreTV.text = favorites.awayScore
        tanggalTV.text = favorites.matchDate

        matchLL.onClick { listener(favorites) }
    }
}