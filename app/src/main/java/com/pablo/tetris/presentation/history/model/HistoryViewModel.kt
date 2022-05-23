package com.pablo.tetris.presentation.history.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.infra.database.PlayerRepository
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByPointsQuery
import com.pablo.tetris.presentation.history.queries.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HistoryViewModel(private val repository: PlayerRepository) : ViewModel() {

    private var query: Query = PlayersOrderedByPointsQuery(this)
    private var result: List<Player> = listOf()
    val updatedDataBase: MutableLiveData<Boolean> = MutableLiveData(false)
    val currentLog: MutableLiveData<String> = MutableLiveData("default value")

    suspend fun query(function: (PlayerRepository) -> List<Player>): List<Player> {
        lateinit var players: List<Player>
        val job = viewModelScope.launch(Dispatchers.IO) { players = function.invoke(repository) }
        job.join()
        return players
    }

    private suspend fun command(command: (PlayerRepository) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { command.invoke(repository) }.join()
        executeQuery()
    }

    fun executeQuery(query: Query = this.query) {
        this.query = query
        this.result = query.get()
        updateDataBaseValue()
    }

    fun getAutoCompleteResult(playerName: String): List<Player> {
        return result.filter { player -> player.name.startsWith(playerName, ignoreCase = true) }
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

    fun showLogForPlayer(player: Player) {
        currentLog.value = "Str"
    }

    class HistoryViewModelFactory(private val repository: PlayerRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HistoryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}