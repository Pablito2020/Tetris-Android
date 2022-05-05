package com.pablo.tetris.domain.game

import block_factory.AllBlockQueueCreator
import block_factory.RandomBlockCreator
import block_factory.SimpleBlockQueueCreator

object BlockGeneratorFactory {

    fun getGenerator(level: Level) = when(level) {
        Level.LOW -> SimpleBlockQueueCreator()
        Level.MEDIUM -> AllBlockQueueCreator()
        Level.HIGH -> RandomBlockCreator()
    }

}