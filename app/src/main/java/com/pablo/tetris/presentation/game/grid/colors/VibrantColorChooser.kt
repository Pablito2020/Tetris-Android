package com.pablo.tetris.presentation.game.grid.colors

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import board.Cell
import com.pablo.tetris.R
import com.pablo.tetris.presentation.game.grid.orientation.layout.OrientedLayout
import game.GameCell

class VibrantColorChooser(private val context: Context, private val layout: OrientedLayout): ColorCellChooser {

    override fun paint(gameCell: GameCell, p1: View?, p2: ViewGroup?): View {
        val view = p1 ?: View.inflate(context, layout.getLayoutItem(), null)
        val cell: TextView = view.findViewById(layout.getIdItem())
        cell.background = AppCompatResources.getDrawable(context, this.getColorForCell(gameCell))
        return view
    }

    private fun getColorForCell(cell: GameCell): Int {
        if (cell.isGhostBlockCell)
            return R.color.vibrant_gray
        return when (cell.cell) {
            Cell.EMPTY -> R.color.black
            Cell.I_BLOCK -> R.color.vibrant_yellow
            Cell.J_BLOCK -> R.color.vibrant_blue
            Cell.L_BLOCK -> R.color.vibrant_cyan
            Cell.SQUARE_BLOCK -> R.color.vibrant_green
            Cell.S_BLOCK -> R.color.vibrant_red
            Cell.T_BLOCK -> R.color.vibrant_magenta
            Cell.Z_BLOCK -> R.color.vibrant_purple
        }
    }

}