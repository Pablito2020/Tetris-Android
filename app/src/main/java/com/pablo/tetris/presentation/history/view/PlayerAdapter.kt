package com.pablo.tetris.presentation.history.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pablo.tetris.R
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.presentation.history.model.HistoryViewModel
import kotlinx.android.synthetic.main.item_player_history.view.*

class PlayerAdapter(
    var viewModel: HistoryViewModel,
    var players: List<Player>
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player_history, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.itemView.apply {
            textView_player_name.text = players[position].name
            textView_player_points.text = players[position].score.toString()
            textView_date_game.text = players[position].date
            textView_level_game.text = players[position].level
            button_delete_game.setOnClickListener {
                viewModel.deletePlayer(players[position])
            }
        }
        holder.itemView.setOnClickListener {
            println("Clicked item")
        }
    }

    override fun getItemCount() = players.size
}