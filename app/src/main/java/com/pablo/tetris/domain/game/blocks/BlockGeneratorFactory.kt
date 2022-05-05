package com.pablo.tetris.domain.game.blocks

import block_factory.AllBlockQueueCreator
import block_factory.RandomBlockCreator
import block_factory.SimpleBlockQueueCreator
import com.pablo.tetris.domain.game.Level

object BlockGeneratorFactory {

    fun getGenerator(level: Level) = when(level) {
        Level.LOW -> SimpleBlockQueueCreator()
        Level.MEDIUM -> AllBlockQueueCreator()
        Level.HIGH -> RandomBlockCreator()
    }

}