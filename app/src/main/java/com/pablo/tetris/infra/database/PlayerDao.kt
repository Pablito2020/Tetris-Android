package com.pablo.tetris.infra.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player_table ORDER BY score ASC")
    fun getPlayersOrderedByScore(): List<Player>

    @Query("SELECT * FROM player_table ORDER BY date DESC")
    fun getPlayersOrderedByDate(): List<Player>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(player: Player)

    @Query("DELETE FROM player_table")
    fun deleteAll()
}