package com.pablo.tetris.presentation.game.grid.style.types

import android.content.Context
import com.pablo.tetris.presentation.game.grid.block.SaturatedBlock
import com.pablo.tetris.presentation.game.grid.colors.VibrantColorChooser
import com.pablo.tetris.presentation.game.grid.style.StyleCreator

class SaturatedStyle(private val context: Context) :
    StyleCreator {
    override fun getBlockCreator() = SaturatedBlock()
    override fun getColorCellChooser() = VibrantColorChooser(context)
}