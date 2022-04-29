package com.pablo.tetris.presentation.game.grid.orientation.layout.textview

import com.pablo.tetris.presentation.game.grid.orientation.LayoutResources

class TextViewGridItem : LayoutResources {
    override fun getVertical() = VerticalTextView()
    override fun getHorizontal() = LandscapeTextView()
}