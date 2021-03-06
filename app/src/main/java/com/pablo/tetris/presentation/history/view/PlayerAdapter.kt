package com.pablo.tetris.presentation.history.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pablo.tetris.R
import com.pablo.tetris.infra.database.Player


class PlayerAdapter(
    var players: List<Player>,
    val deleteButtonListener: (player: Player) -> Unit,
    val itemClickListener: (player: Player) -> Unit,
    val itemLongCLickListener: (view: View, player: Player) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_player_history, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.textView_player_name).text = players[position].name
            findViewById<TextView>(R.id.textView_player_points).text =
                players[position].score.toString()
            findViewById<TextView>(R.id.textView_date_game).text = players[position].date
            findViewById<TextView>(R.id.textView_level_game).text = players[position].level
            findViewById<Button>(R.id.button_delete_game).setOnClickListener { deleteButtonListener(players[position]) }
        }
        holder.itemView.setOnClickListener { itemClickListener(players[position]) }
        holder.itemView.setOnLongClickListener {
            itemLongCLickListener(holder.itemView, players[position])
            true
        }
    }

    override fun getItemCount() = players.size
}