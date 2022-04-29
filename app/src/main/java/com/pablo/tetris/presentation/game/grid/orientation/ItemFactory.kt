package com.pablo.tetris.presentation.game.grid.orientation

import android.content.res.Configuration

object ItemFactory {

    fun getItem(orientation: Int) = when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> LandscapeItemGetter()
        Configuration.ORIENTATION_PORTRAIT -> VerticalItemGetter()
        else -> throw IllegalArgumentException("Unknown orientation: $orientation")
    }

}