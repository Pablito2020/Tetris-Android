package com.pablo.tetris.domain.game.logs

import com.pablo.tetris.presentation.common.UiText

interface Logger {
    fun add(message: UiText)
    fun getLog(): List<UiText>
    fun clear()
}