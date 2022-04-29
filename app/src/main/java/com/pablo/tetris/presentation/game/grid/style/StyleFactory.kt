package com.pablo.tetris.presentation.game.grid.style

import android.content.Context
import com.pablo.tetris.presentation.game.grid.orientation.layout.imageview.ImageViewGridItem
import com.pablo.tetris.presentation.game.grid.orientation.layout.textview.TextViewGridItem
import com.pablo.tetris.presentation.game.grid.style.types.NeonStyle
import com.pablo.tetris.presentation.game.grid.style.types.SaturatedStyle


object StyleFactory {

    fun getStyleCreator(style: Style, context: Context, orientation: Int) = when (style) {
        Style.SATURATED -> SaturatedStyle(context, TextViewGridItem().fromOrientation(orientation))
        Style.NEON -> NeonStyle(context, ImageViewGridItem().fromOrientation(orientation))
    }

}