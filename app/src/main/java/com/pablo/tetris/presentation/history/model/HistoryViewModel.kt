package com.pablo.tetris.presentation.history.model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.infra.database.PlayerApplication
import com.pablo.tetris.infra.database.PlayerRepository
import com.pablo.tetris.presentation.history.commands.Command
import com.pablo.tetris.presentation.history.commands.EmptyCommand
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByPointsQuery
import com.pablo.tetris.presentation.history.queries.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : ViewModel() {

    private val repository = (application as PlayerApplication).repository
    private var query: Query = PlayersOrderedByPointsQuery(this)
    private var command: Command = EmptyCommand()
    val updatedDataBase: MutableLiveData<Boolean> = MutableLiveData(false)

    suspend fun query(function: (PlayerRepository) -> List<Player>): List<Player> {
        lateinit var players: List<Player>
        val job = viewModelScope.launch(Dispatchers.IO){ players = function.invoke(repository) }
        job.join()
        return players
    }

    suspend fun command(command: (PlayerRepository) -> Unit) {
        viewModelScope.launch(Dispatchers.IO){ command.invoke(repository) }.join()
    }

    fun executeAction(action: Action) {
        when (action) {
            is Action.Query -> setQueryAction(action)
            is Action.Command -> setCommandAction(action)
        }
        updatedDataBase.value = true
    }

    private fun setQueryAction(action: Action.Query) {
        this.query = action.query
    }

    private fun setCommandAction(action: Action.Command) {
        this.command = action.command
    }

    fun getPlayers() = query.get()

    fun executeCommand(player: Player) {
        command.execute(player)
        updatedDataBase.value = true
    }

}