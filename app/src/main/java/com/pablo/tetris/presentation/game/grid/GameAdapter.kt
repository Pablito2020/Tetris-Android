package com.pablo.tetris.presentation.game.grid

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.pablo.tetris.presentation.game.grid.orientation.ItemGetter
import com.pablo.tetris.presentation.game.grid.colors.ColorCellChooser
import com.pablo.tetris.presentation.game.grid.colors.VibrantColorChooser
import game.GameCell

class GameAdapter(
    var gameCells: List<GameCell>,
    private val context: Context,
    private val colorChooser: ColorCellChooser = VibrantColorChooser(),
    private val itemGetter: ItemGetter
) : BaseAdapter() {

    override fun getCount(): Int = 10 * 20

    override fun getItem(p0: Int): Any = gameCells[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = p1 ?: View.inflate(context, itemGetter.getLayoutItem(), null)
        val cell: TextView = view.findViewById(itemGetter.getIdItem())
        cell.background =
            AppCompatResources.getDrawable(context, colorChooser.getColorForCell(gameCells[p0]))!!
        return view
    }

}