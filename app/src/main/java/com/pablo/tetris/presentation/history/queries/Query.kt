package com.pablo.tetris.presentation.history.queries

import com.pablo.tetris.infra.database.Player

interface Query {
    fun get(): List<Player>
}