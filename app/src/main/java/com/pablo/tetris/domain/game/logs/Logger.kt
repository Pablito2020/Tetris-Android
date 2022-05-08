package com.pablo.tetris.domain.game.logs

import android.content.Context

interface Logger {
    fun add(message: String, context: Context)
    fun getLog(context: Context): List<String>
    fun clear()
}