package com.pablo.tetris.presentation.game.grid.style.types

import android.content.Context
import com.pablo.tetris.presentation.game.grid.block.NeonBlock
import com.pablo.tetris.presentation.game.grid.colors.NeonImageChooser
import com.pablo.tetris.presentation.game.grid.style.StyleCreator

class NeonStyle(private val context: Context) : StyleCreator {
    override fun getBlockCreator() = NeonBlock()
    override fun getColorCellChooser() = NeonImageChooser(context)
}