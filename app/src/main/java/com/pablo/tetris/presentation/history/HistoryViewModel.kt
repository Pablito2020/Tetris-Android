package com.pablo.tetris.presentation.history

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.infra.database.PlayerApplication
import com.pablo.tetris.infra.database.PlayerRepository
import com.pablo.tetris.presentation.history.commands.DeletePlayerCommand
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByPointsQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : ViewModel() {

    private val repository = (application as PlayerApplication).repository
    private val query = PlayersOrderedByPointsQuery(this)
    private val command = DeletePlayerCommand(this)

    suspend fun query(function: (PlayerRepository) -> List<Player>): List<Player> {
        lateinit var players: List<Player>
        val job = viewModelScope.launch(Dispatchers.IO){ players = function.invoke(repository) }
        job.join()
        return players
    }

    suspend fun command(command: (PlayerRepository) -> Unit) {
        viewModelScope.launch(Dispatchers.IO){ command.invoke(repository) }.join()
    }

    fun getPlayers() = query.get()

    fun executeAction(player: Player) = command.execute(player)

}