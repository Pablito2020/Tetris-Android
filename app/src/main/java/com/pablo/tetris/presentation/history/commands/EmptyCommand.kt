package com.pablo.tetris.presentation.history.commands

import com.pablo.tetris.infra.database.Player

class EmptyCommand: Command {
    override fun execute(player: Player) {}
}