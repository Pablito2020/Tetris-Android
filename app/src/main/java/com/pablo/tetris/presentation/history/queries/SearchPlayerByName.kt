package com.pablo.tetris.presentation.history.queries

import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.presentation.history.model.HistoryViewModel
import kotlinx.coroutines.runBlocking


class SearchPlayerByName(val viewModel: HistoryViewModel, private val name: String) : Query {

    override fun get(): List<Player> {
        val result = mutableListOf<Player>()
        run {
            runBlocking {
                if (name.isNotBlank())
                    result.addAll(viewModel.query { repo -> repo.getPlayersThatMatch(name) })
            }
        }
        return result
    }

}
