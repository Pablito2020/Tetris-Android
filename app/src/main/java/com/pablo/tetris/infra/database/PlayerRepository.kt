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

    fun getPlayersThatMatch(search: String) = playerDao.getPlayersThatMatch(search)

    fun deletePlayer(id: Int) = playerDao.deletePlayer(id)

    fun getLogForPlayer(id: Int) = playerDao.getLogFromPlayer(id)

    fun getPlayer(id: Int): Player? = playerDao.getPlayer(id)

}