package com.pablo.tetris.infra.logs

import com.pablo.tetris.R
import com.pablo.tetris.domain.game.logs.Logger
import com.pablo.tetris.presentation.common.UiText

object LoggerGetter {
    fun get(): Logger = MemoryLogger
}

object LoggerConstants {
    val MOVE_DOWN = UiText.ResourceString(R.string.down_movement)
    val MOVE_LEFT = UiText.ResourceString(R.string.left_movement)
    val MOVE_RIGHT = UiText.ResourceString(R.string.right_movement)
    val ROTATE_LEFT = UiText.ResourceString(R.string.left_rotation)
    val ROTATE_RIGHT = UiText.ResourceString(R.string.right_rotation)
    val DROP_DOWN = UiText.ResourceString(R.string.drop_down)
}