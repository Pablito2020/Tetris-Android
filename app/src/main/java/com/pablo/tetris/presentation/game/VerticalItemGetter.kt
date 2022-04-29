package com.pablo.tetris.presentation.game

import com.pablo.tetris.R

class VerticalItemGetter: ItemGetter {
    override fun getLayoutItem() = R.layout.grid_item
    override fun getIdItem() = R.id.grid_item
}