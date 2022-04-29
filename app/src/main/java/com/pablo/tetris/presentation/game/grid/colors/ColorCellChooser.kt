package com.pablo.tetris.presentation.game.grid.colors

import game.GameCell

interface ColorCellChooser {
    fun getColorForCell(cell: GameCell): Int
}