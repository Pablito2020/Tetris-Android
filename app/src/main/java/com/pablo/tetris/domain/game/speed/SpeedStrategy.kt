package com.pablo.tetris.domain.game.speed

import score.Points

interface SpeedStrategy {
    fun getSpeedInMilliseconds(points: Points): Long
}