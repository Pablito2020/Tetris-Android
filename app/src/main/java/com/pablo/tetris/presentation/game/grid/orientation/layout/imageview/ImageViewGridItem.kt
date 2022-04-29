package com.pablo.tetris.presentation.game.grid.orientation.layout.imageview

import com.pablo.tetris.presentation.game.grid.orientation.LayoutResources

class ImageViewGridItem : LayoutResources {

    override fun getVertical() = VerticalImageView()

    override fun getHorizontal() = LandscapeImageView()

}