package com.pablo.tetris.presentation.history.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pablo.tetris.R
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.presentation.history.CurrentGameLogActivity
import com.pablo.tetris.presentation.history.model.HistoryViewModel


class PlayerAdapter(
    var viewModel: HistoryViewModel,
    var players: List<Player>,
    private val hasLogFragment: Boolean,
    val context: Context
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
            findViewById<Button>(R.id.button_delete_game).setOnClickListener {
                showDeleteDialog(players[position])
            }
        }
        holder.itemView.setOnClickListener {
            viewModel.showLogForPlayer(players[position])
            if (!hasLogFragment) {
                val intent = Intent(context.applicationContext, CurrentGameLogActivity::class.java)
                context.startActivity(intent)
            }
        }
        holder.itemView.setOnLongClickListener {
            val popupMenu = PopupMenu(context, holder.itemView)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.getMenu())
            popupMenu.setOnMenuItemClickListener {
                    this.players = viewModel.getAutoCompleteResult(players[position].name)
                    notifyDataSetChanged()
                    true
                }
            popupMenu.show()
            true
        }
    }

    private fun showDeleteDialog(player: Player) {
        androidx.appcompat.app.AlertDialog.Builder(context)
            .setTitle(R.string.warning)
            .setMessage(context.getString(R.string.delete_game_warning, player.name))
            .setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(context, R.string.deletion_canceled, Toast.LENGTH_SHORT).show() }
            .setPositiveButton(R.string.ok) { _, _ -> viewModel.deletePlayer(player); Toast.makeText(context, R.string.deleted_player_game, Toast.LENGTH_SHORT).show() }
            .create()
            .show()
    }

    override fun getItemCount() = players.size
}