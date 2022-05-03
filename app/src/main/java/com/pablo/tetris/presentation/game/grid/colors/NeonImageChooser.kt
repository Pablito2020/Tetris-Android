package com.pablo.tetris.presentation.game.grid.colors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import board.Cell
import com.pablo.tetris.R
import game.GameCell


class NeonImageChooser(private val context: Context) :
    ColorCellChooser {

    override fun paint(gameCell: GameCell, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.grid_item_image, null)
        val imageView: ImageView = convertView!!.findViewById(R.id.grid_item_image)
        imageView.setImageResource(getResourceForCell(gameCell))
        val params = imageView.layoutParams
        params.width = parent!!.width / 10
        params.height = parent.height / 20
        return convertView
    }

    private fun getResourceForCell(cell: GameCell): Int {
        if (cell.isGhostBlockCell)
            return R.drawable.ghostcellneon
        return when (cell.cell) {
            Cell.EMPTY -> R.drawable.blackcellneon
            Cell.I_BLOCK -> R.drawable.cyancellneon
            Cell.J_BLOCK -> R.drawable.greencellneon
            Cell.L_BLOCK -> R.drawable.orangecellneon
            Cell.SQUARE_BLOCK -> R.drawable.bluecellneon
            Cell.S_BLOCK -> R.drawable.purplecellneon
            Cell.T_BLOCK -> R.drawable.redcellneon
            Cell.Z_BLOCK -> R.drawable.yellowcellneon
        }
    }

}