package com.pablo.tetris.presentation.game.grid.orientation.layout.imageview

import com.pablo.tetris.R
import com.pablo.tetris.presentation.game.grid.orientation.layout.OrientedLayout

class VerticalImageView: OrientedLayout {
    override fun getLayoutItem() = R.layout.grid_item_image
    override fun getIdItem() = R.id.grid_item_image
}