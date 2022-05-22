package com.pablo.tetris

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.infra.database.PlayerApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    suspend fun getPlayersOrderedByScore(application: Application): List<Player> {
        lateinit var players: List<Player>
        val job = viewModelScope.launch(Dispatchers.IO){ players = (application as PlayerApplication).repository.getPlayersOrderedByScore()}
        job.join()
        return players
    }
}