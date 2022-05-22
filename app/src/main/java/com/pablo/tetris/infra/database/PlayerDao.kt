package com.pablo.tetris.infra.database

import androidx.room.*

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player_table ORDER BY score DESC")
    fun getPlayersOrderedByScore(): List<Player>

    @Query("SELECT * FROM player_table ORDER BY date ASC")
    fun getPlayersOrderedByDate(): List<Player>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(player: Player)

    @Query("DELETE FROM player_table")
    fun deleteAll()

    @Query("SELECT * FROM player_table WHERE name LIKE :search || '%'")
    fun getPlayersThatMatch(search: String): List<Player>

    @Query("DELETE FROM player_table WHERE id = :id")
    fun deletePlayer(id: Int)
}