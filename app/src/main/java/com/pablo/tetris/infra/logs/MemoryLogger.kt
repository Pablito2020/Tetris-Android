package com.pablo.tetris.infra.logs

import android.content.Context
import com.pablo.tetris.R
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

    override fun add(message: String, context: Context) {
        when (message) {
            MOVE_DOWN.asString(context), MOVE_LEFT.asString(context), MOVE_RIGHT.asString(context), ROTATE_LEFT.asString(context), ROTATE_RIGHT.asString(context), DROP_DOWN.asString(context) -> {
                movements[message] = movements.getOrElse(message) { 0 }.plus(1)
            }
            else -> messages.add(message)
        }
    }

    override fun getLog(context: Context): List<String> {
        val result = messages.toMutableList()
        result.addAll(movements.map { context.getString(R.string.result_movements_log, it.key, it.value)})
        return result
    }

    override fun clear() {
        messages.clear()
        movements.clear()
    }

}