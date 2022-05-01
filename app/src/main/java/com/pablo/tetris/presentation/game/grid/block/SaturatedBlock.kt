package com.pablo.tetris.presentation.game.grid.block

import block_factory.BlockType
import com.pablo.tetris.R

class SaturatedBlock : BlockPainter {

    override fun getImageId(block: BlockType) = when (block) {
        BlockType.I_BLOCK -> R.drawable.iblocksaturated
        BlockType.S_BLOCK -> R.drawable.sblocksaturated
        BlockType.Z_BLOCK -> R.drawable.zblocksaturated
        BlockType.L_BLOCK -> R.drawable.lblocksaturated
        BlockType.J_BLOCK -> R.drawable.jblocksaturated
        BlockType.SQUARE_BLOCK -> R.drawable.squareblocksaturated
        BlockType.T_BLOCK -> R.drawable.tblocksaturated
    }

}