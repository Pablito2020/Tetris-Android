package com.pablo.tetris.domain.game

import block_factory.RandomBlockCreator

object BlockGeneratorFactory {

    //TODO add more block generators on Low and med
    fun getGenerator(level: Level) = when(level) {
        Level.LOW -> RandomBlockCreator()
        Level.MEDIUM -> RandomBlockCreator()
        Level.HIGH -> RandomBlockCreator()
    }

}