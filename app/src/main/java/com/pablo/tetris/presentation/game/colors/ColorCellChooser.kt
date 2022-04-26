package com.pablo.tetris.presentation.game.colors

import game.GameCell

interface ColorCellChooser {
    fun getColorForCell(cell: GameCell): Int
}