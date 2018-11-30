package com.example.andiwijaya.submission3.favorites.favoriteteams

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.model.FavoriteTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class FavoriteTeamsAdapter(
    private val favoriteTeams: List<FavoriteTeam>,
    private val listener: (FavoriteTeam) -> Unit
) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_team,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favoriteTeams[position], listener)
    }

    override fun getItemCount(): Int = favoriteTeams.size

}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val teamBadge: ImageView = view.find(R.id.team_badge)
    private val teamName: TextView = view.find(R.id.team_name)

    fun bindItem(favorites: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        Picasso.get().load(favorites.teamBadge).into(teamBadge)
        teamName.text = favorites.teamName
        itemView.setOnClickListener { listener(favorites) }
    }
}