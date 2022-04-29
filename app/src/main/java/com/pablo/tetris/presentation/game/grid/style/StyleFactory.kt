package com.pablo.tetris.presentation.game.grid.style


object StyleFactory {

    fun getStyleCreator(style: Style) = when (style) {
        Style.SATURATED -> SaturatedStyle()
    }

}