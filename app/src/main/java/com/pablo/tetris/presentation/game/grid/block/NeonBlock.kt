package com.pablo.tetris.presentation.game.grid.block

import block_factory.BlockType
import com.pablo.tetris.R

class NeonBlock: BlockPainter {

    override fun getImageId(block: BlockType) = when(block) {
        BlockType.I_BLOCK -> R.drawable.iblockneon
        BlockType.S_BLOCK -> R.drawable.sblockneon
        BlockType.Z_BLOCK -> R.drawable.zblockneon
        BlockType.L_BLOCK -> R.drawable.lblockneon
        BlockType.J_BLOCK -> R.drawable.jblockneon
        BlockType.SQUARE_BLOCK -> R.drawable.squareblockneon
        BlockType.T_BLOCK -> R.drawable.tblockneon
    }

}