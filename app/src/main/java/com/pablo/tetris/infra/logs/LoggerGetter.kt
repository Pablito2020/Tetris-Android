package com.pablo.tetris.infra.logs

import com.pablo.tetris.domain.game.logs.Logger

object LoggerGetter {
    fun get(): Logger = MemoryLogger
}