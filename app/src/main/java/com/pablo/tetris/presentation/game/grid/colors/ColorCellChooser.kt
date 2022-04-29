package com.pablo.tetris.presentation.game.grid.colors

import android.view.View
import android.view.ViewGroup
import game.GameCell

interface ColorCellChooser {
    fun paint(gameCell: GameCell, p1: View?, parent: ViewGroup?): View
}