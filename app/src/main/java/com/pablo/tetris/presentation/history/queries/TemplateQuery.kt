package com.pablo.tetris.presentation.history.queries

import com.pablo.tetris.infra.database.Player
import kotlinx.coroutines.runBlocking

abstract class TemplateQuery: Query {
    override fun get(): List<Player> {
        lateinit var result: List<Player>
        run {
            runBlocking {
                result = getResult()
            }
        }
        return result
    }

    abstract suspend fun getResult(): List<Player>

}