package com.pablo.tetris.presentation.game.grid.style

import com.pablo.tetris.presentation.game.grid.block.BlockPainter
import com.pablo.tetris.presentation.game.grid.colors.ColorCellChooser

interface StyleCreator {
    fun getBlockCreator() : BlockPainter
    fun getColorCellChooser(): ColorCellChooser
}