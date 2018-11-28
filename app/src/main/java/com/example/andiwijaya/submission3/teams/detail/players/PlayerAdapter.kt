package com.example.andiwijaya.submission3.teams.detail.players

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk27.coroutines.onClick

class PlayerAdapter(private val players: MutableList<Player>, private val listener: (Player) -> Unit) :
    RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PlayerViewHolder {
        return PlayerViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.item_player,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(p0: PlayerViewHolder, p1: Int) {
        p0.bindItem(players[p1], listener)
    }

}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val playerCardView = view.findViewById<CardView>(R.id.playerCV)
    val playerImage = view.findViewById<ImageView>(R.id.playerIV)
    val playerName = view.findViewById<TextView>(R.id.playerNameTV)
    val playerPosition = view.findViewById<TextView>(R.id.playerPositionTV)

    fun bindItem(players: Player, listener: (Player) -> Unit) {
        if (!players.playerImage.isNullOrEmpty()) {
            Picasso.get().load(players.playerImage).into(playerImage)
        }
        playerName.text = players.playerName
        playerPosition.text = players.playerPosition

        playerCardView.onClick { listener(players) }
    }

}