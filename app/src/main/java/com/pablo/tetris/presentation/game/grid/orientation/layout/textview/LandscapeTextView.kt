package com.pablo.tetris.presentation.game.grid.orientation.layout.textview

import com.pablo.tetris.R
import com.pablo.tetris.presentation.game.grid.orientation.layout.OrientedLayout

class LandscapeTextView : OrientedLayout {
    override fun getLayoutItem() = R.layout.grid_item_small
    override fun getIdItem() = R.id.grid_item_small
}