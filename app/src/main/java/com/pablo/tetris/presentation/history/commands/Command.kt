package com.pablo.tetris.presentation.history.commands

import com.pablo.tetris.infra.database.Player

interface Command {
    fun execute(player: Player)
}