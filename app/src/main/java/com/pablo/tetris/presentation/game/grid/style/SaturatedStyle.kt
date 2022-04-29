package com.pablo.tetris.presentation.game.grid.style

import com.pablo.tetris.presentation.game.grid.block.SaturatedBlock
import com.pablo.tetris.presentation.game.grid.colors.VibrantColorChooser

class SaturatedStyle: StyleCreator {
    override fun getBlockCreator() = SaturatedBlock()
    override fun getColorCellChooser() = VibrantColorChooser()
}