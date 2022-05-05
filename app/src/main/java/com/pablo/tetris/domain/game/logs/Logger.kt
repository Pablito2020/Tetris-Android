package com.pablo.tetris.domain.game.logs

interface Logger {
    fun add(message: String)
    fun getLog(): List<String>
    fun clear()
}