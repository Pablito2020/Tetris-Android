package com.pablo.tetris.infra.database

import androidx.annotation.WorkerThread

class PlayerRepository(private val playerDao: PlayerDao) {

    fun getPlayersOrderedByScore() = playerDao.getPlayersOrderedByScore()

    fun getPlayersOrderedByDate() = playerDao.getPlayersOrderedByDate()

    @WorkerThread
    fun insert(player: Player) {
        playerDao.insert(player)
    }

    fun deleteAll() = playerDao.deleteAll()

}