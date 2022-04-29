package com.pablo.tetris.presentation.game.grid.colors

import android.view.View
import game.GameCell

interface ColorCellChooser {
    fun paint(gameCell: GameCell, p1: View?): View
}