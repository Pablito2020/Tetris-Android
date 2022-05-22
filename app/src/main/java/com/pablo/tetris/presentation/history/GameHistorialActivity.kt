package com.pablo.tetris.presentation.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pablo.tetris.R
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.infra.database.PlayerApplication
import com.pablo.tetris.presentation.history.commands.DeletePlayerCommand
import kotlinx.android.synthetic.main.activity_game_historial.*
import kotlinx.coroutines.runBlocking

class GameHistorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_historial)

        val historyViewModel = HistoryViewModel(application)

        val adapter = PlayerAdapter(historyViewModel)
        setUpRecyclerView(adapter)
    }

    private fun setUpRecyclerView(adapter: PlayerAdapter) {
        recyclerViewHistory.adapter = adapter
        val manager = LinearLayoutManager(this)
        recyclerViewHistory.layoutManager = manager
        recyclerViewHistory.addItemDecoration(DividerItemDecoration(recyclerViewHistory.context, manager.orientation))
    }
}