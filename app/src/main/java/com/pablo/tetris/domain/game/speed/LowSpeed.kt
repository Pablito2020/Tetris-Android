package com.pablo.tetris.domain.game.speed

import score.Points

class LowSpeed : SpeedStrategy {
    override fun getSpeedInMilliseconds(points: Points) = when(points.value) {
        in 0 until 200 -> 1000L
        in 200 until 600 -> 900L
        in 600 until 1000 -> 800L
        in 1000 until 1300 -> 700L
        in 1300 until 1500 -> 600L
        in 1500 until 1800 -> 500L
        in 1800 until 2000 -> 400L
        else -> 350L
    }
}