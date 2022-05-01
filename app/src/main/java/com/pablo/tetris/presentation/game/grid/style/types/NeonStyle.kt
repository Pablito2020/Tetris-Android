package com.pablo.tetris.presentation.game.grid.style.types

import android.content.Context
import com.pablo.tetris.presentation.game.grid.block.NeonBlock
import com.pablo.tetris.presentation.game.grid.block.SaturatedBlock
import com.pablo.tetris.presentation.game.grid.colors.NeonImageChooser
import com.pablo.tetris.presentation.game.grid.orientation.layout.OrientedLayout
import com.pablo.tetris.presentation.game.grid.style.StyleCreator

class NeonStyle(private val context: Context, private val layout: OrientedLayout) : StyleCreator {
    override fun getBlockCreator() = NeonBlock()
    override fun getColorCellChooser() = NeonImageChooser(context, layout)
}