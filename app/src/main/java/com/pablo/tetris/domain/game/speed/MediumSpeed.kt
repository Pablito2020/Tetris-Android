package com.pablo.tetris.domain.game.speed

import score.Points

class MediumSpeed: SpeedStrategy {

    override fun getSpeedInMilliseconds(points: Points) = when(points.value) {
        in 0 until 600 -> 1000L
        in 600 until 1200 -> 900L
        in 1200 until 1500 -> 800L
        in 1500 until 1800 -> 700L
        else -> 650L
    }

}