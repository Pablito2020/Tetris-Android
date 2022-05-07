package com.pablo.tetris.infra.logs

import com.pablo.tetris.domain.game.logs.Logger
import com.pablo.tetris.infra.logs.LoggerConstants.DROP_DOWN
import com.pablo.tetris.infra.logs.LoggerConstants.MOVE_DOWN
import com.pablo.tetris.infra.logs.LoggerConstants.MOVE_LEFT
import com.pablo.tetris.infra.logs.LoggerConstants.MOVE_RIGHT
import com.pablo.tetris.infra.logs.LoggerConstants.ROTATE_LEFT
import com.pablo.tetris.infra.logs.LoggerConstants.ROTATE_RIGHT

object MemoryLogger : Logger {

    val messages: MutableList<String> = mutableListOf()
    val movements: MutableMap<String, Int> = mutableMapOf()

    override fun add(message: String) {
        when (message) {
            MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, ROTATE_LEFT, ROTATE_RIGHT, DROP_DOWN -> {
                movements[message] = movements.getOrElse(message) { 0 }.plus(1)
            }
            else -> messages.add(message)
        }
    }

    override fun getLog(): List<String> {
        messages.addAll(movements.map { "Did a ${it.key} ${it.value} times." })
        return messages
    }

    override fun clear() = messages.clear()

}