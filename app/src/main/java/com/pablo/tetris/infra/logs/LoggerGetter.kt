package com.pablo.tetris.infra.logs

import com.pablo.tetris.domain.game.logs.Logger

object LoggerGetter {
    fun get(): Logger = MemoryLogger
}

object LoggerConstants {
    const val MOVE_DOWN = "down movement"
    const val MOVE_LEFT = "left movement"
    const val MOVE_RIGHT = "right movement"
    const val ROTATE_LEFT = "left rotation"
    const val ROTATE_RIGHT = "right rotation"
    const val DROP_DOWN = "down drop"
}