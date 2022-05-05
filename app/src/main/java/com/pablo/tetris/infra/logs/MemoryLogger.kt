package com.pablo.tetris.infra.logs

import com.pablo.tetris.domain.game.logs.Logger

object MemoryLogger: Logger {

    val messages: MutableList<String> = mutableListOf()

    override fun add(message: String) {
        messages.add(message)
    }

    override fun getLog() = messages

    override fun clear() = messages.clear()

}