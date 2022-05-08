package com.pablo.tetris.domain.game.speed

import score.Points

class MediumSpeed: SpeedStrategy {

    override fun getSpeedInMilliseconds(points: Points) = when(points.value) {
        in 0 until 200 -> 1000L
        in 200 until 600 -> 900L
        in 600 until 800 -> 800L
        in 800 until 1000 -> 700L
        in 900 until 1200 -> 600L
        in 1200 until 1400 -> 550L
        in 1400 until 1600 -> 500L
        else -> 400L
    }

}