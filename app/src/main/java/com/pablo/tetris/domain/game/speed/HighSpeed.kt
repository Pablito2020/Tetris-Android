package com.pablo.tetris.domain.game.speed

import score.Points

class HighSpeed: SpeedStrategy {

    override fun getSpeedInMilliseconds(points: Points) = when(points.value) {
        in 0 until 200 -> 1000L
        in 200 until 500 -> 900L
        in 500 until 700 -> 800L
        in 700 until 1000 -> 600L
        in 1000 until 1200 -> 550L
        in 1200 until 1400 -> 450L
        else -> 350L
    }

}