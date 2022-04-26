package com.pablo.tetris.presentation.game

import game.GameCell

interface ColorCellChooser {
    fun getColorForCell(cell: GameCell): Int
}