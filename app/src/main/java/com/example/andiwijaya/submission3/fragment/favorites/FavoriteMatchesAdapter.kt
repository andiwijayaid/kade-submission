package com.example.andiwijaya.submission3.fragment.favorites

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.detail.MatchDetailActivity
import com.example.andiwijaya.submission3.model.Favorite
import org.jetbrains.anko.startActivity

class FavoriteMatchesAdapter(private val favorite: List<Favorite>, private val context: Context)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.match_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position])

        holder.matchLL.setOnClickListener {
            context.startActivity<MatchDetailActivity>("FILE_NAME" to favorite[position].fileName,
                                                        "ID" to favorite[position].matchId)
        }
    }

    override fun getItemCount(): Int = favorite.size

}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){

    val homeNameTV: TextView = view.findViewById(R.id.homeNameTV)
    val homeScoreTV: TextView = view.findViewById(R.id.homeScoreTV)
    val awayNameTV: TextView = view.findViewById(R.id.awayNameTV)
    val awayScoreTV: TextView = view.findViewById(R.id.awayScoreTV)
    val tanggalTV: TextView = view.findViewById(R.id.tanggalTV)
    val matchLL: LinearLayout = view.findViewById(R.id.matchLL)

    fun bindItem(favorites: Favorite) {
        homeNameTV.text = favorites.homeName
        awayNameTV.text = favorites.awayName
        homeScoreTV.text = favorites.homeScore
        awayScoreTV.text = favorites.awayScore
        tanggalTV.text = favorites.matchDate
    }
}