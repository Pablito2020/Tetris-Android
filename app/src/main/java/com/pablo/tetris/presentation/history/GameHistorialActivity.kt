package com.pablo.tetris.presentation.history

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pablo.tetris.R
import com.pablo.tetris.presentation.history.model.HistoryViewModel
import com.pablo.tetris.presentation.history.view.PlayerAdapter
import com.pablo.tetris.presentation.history.view.Spinner
import kotlinx.android.synthetic.main.activity_game_historial.*

class GameHistorialActivity : AppCompatActivity() {

    private val historyViewModel: HistoryViewModel by lazy {
        HistoryViewModel(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_historial)
        setUpRecyclerView()
        setUpSpinner()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecyclerView() {
        val adapter = PlayerAdapter(historyViewModel)
        recyclerViewHistory.adapter = adapter
        val manager = LinearLayoutManager(this)
        recyclerViewHistory.layoutManager = manager
        recyclerViewHistory.addItemDecoration(DividerItemDecoration(recyclerViewHistory.context, manager.orientation))
        historyViewModel.updatedDataBase.observe(this) { adapter.notifyDataSetChanged() }
    }

    private fun setUpSpinner() {
        chooseActionSpinner.onItemSelectedListener = Spinner(historyViewModel)
    }

}