package com.pablo.tetris.presentation.game.grid.orientation

import android.content.res.Configuration
import com.pablo.tetris.presentation.game.grid.orientation.layout.OrientedLayout

interface LayoutResources {

    fun getVertical(): OrientedLayout

    fun getHorizontal(): OrientedLayout

    fun fromOrientation(orientation: Int) = when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> getHorizontal()
        Configuration.ORIENTATION_PORTRAIT -> getVertical()
        else -> throw IllegalArgumentException("Unknown orientation: $orientation")
    }

}
