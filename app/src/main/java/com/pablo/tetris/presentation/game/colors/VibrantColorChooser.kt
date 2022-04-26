package com.pablo.tetris.presentation.game.colors

import board.Cell
import com.pablo.tetris.R
import game.GameCell

class VibrantColorChooser: ColorCellChooser {

    override fun getColorForCell(gameCell: GameCell): Int {
        if (gameCell.isGhostBlockCell)
            return R.color.vibrant_gray
        return when (gameCell.cell) {
            Cell.EMPTY -> R.color.black
            Cell.I_BLOCK -> R.color.vibrant_yellow
            Cell.J_BLOCK -> R.color.vibrant_blue
            Cell.L_BLOCK -> R.color.vibrant_cyan
            Cell.SQUARE_BLOCK -> R.color.vibrant_green
            Cell.S_BLOCK -> R.color.vibrant_red
            Cell.T_BLOCK -> R.color.vibrant_magenta
            Cell.Z_BLOCK -> R.color.vibrant_white
        }
    }

}