package com.pablo.tetris.presentation.game.grid.orientation.layout.imageview

import com.pablo.tetris.R
import com.pablo.tetris.presentation.game.grid.orientation.layout.OrientedLayout

class LandscapeImageView: OrientedLayout {
    override fun getLayoutItem() = R.layout.grid_item_image_small
    override fun getIdItem() = R.id.grid_item_image_small
}