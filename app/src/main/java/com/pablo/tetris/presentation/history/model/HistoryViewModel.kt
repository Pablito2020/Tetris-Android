package com.pablo.tetris.presentation.history.model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.infra.database.PlayerApplication
import com.pablo.tetris.infra.database.PlayerRepository
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByPointsQuery
import com.pablo.tetris.presentation.history.queries.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HistoryViewModel(application: Application) : ViewModel() {

    private val repository = (application as PlayerApplication).repository
    private var query: Query = PlayersOrderedByPointsQuery(this)
    private var result: List<Player> = listOf()
    val updatedDataBase: MutableLiveData<Boolean> = MutableLiveData(false)

    suspend fun query(function: (PlayerRepository) -> List<Player>): List<Player> {
        lateinit var players: List<Player>
        val job = viewModelScope.launch(Dispatchers.IO){ players = function.invoke(repository) }
        job.join()
        return players
    }

    private suspend fun command(command: (PlayerRepository) -> Unit) {
        viewModelScope.launch(Dispatchers.IO){ command.invoke(repository) }.join()
    }

    fun executeQuery(query: Query = this.query) {
        this.query = query
        this.result = query.get()
        updateDataBaseValue()
    }

    fun getPlayers(): List<Player> {
        return result
    }

    private fun updateDataBaseValue() {
        updatedDataBase.value = updatedDataBase.value != true
    }

    fun deletePlayer(player: Player) {
        runBlocking {
            command { repo -> repo.deletePlayer(player.id) }
        }
        updateDataBaseValue()
    }

}