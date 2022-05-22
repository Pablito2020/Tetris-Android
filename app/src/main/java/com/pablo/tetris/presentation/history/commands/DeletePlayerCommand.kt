package com.pablo.tetris.presentation.history.commands

import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.presentation.history.HistoryViewModel
import kotlinx.coroutines.runBlocking

class DeletePlayerCommand(val viewModel: HistoryViewModel, val successExecution: () -> Unit = {}) :
    Command {
    override fun execute(player: Player) {
        run {
            runBlocking {
                viewModel.command({ repo -> repo.deletePlayer(player.id) })
            }
            successExecution()
        }
    }
}