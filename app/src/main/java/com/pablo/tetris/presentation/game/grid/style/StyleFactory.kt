package com.pablo.tetris.presentation.game.grid.style

import android.content.Context
import com.pablo.tetris.presentation.game.grid.style.types.NeonStyle
import com.pablo.tetris.presentation.game.grid.style.types.SaturatedStyle


object StyleFactory {

    fun getStyleCreator(style: Style, context: Context) = when (style) {
        Style.SATURATED -> SaturatedStyle(context)
        Style.NEON -> NeonStyle(context)
    }

}