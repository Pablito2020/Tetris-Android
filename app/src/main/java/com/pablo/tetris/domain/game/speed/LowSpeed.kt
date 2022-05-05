package com.pablo.tetris.domain.game.speed

import score.Points

class LowSpeed : SpeedStrategy {
    override fun getSpeedInMilliseconds(points: Points) = when(points.value) {
        in 0 until 600 -> 1000L
        in 600 until 1200 -> 900L
        else -> 750L
    }
}