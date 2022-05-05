package com.pablo.tetris.presentation.game.results

import java.io.Serializable

data class GameResult(
    val score: String,
    val date: String
) : Serializable
