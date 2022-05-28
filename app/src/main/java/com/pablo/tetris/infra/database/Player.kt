package com.pablo.tetris.infra.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class Player(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                  @ColumnInfo(name="name") val name: String,
                  @ColumnInfo(name="score") val score: Int,
                  @ColumnInfo(name="level") val level: String,
                  @ColumnInfo(name="date") val date: String,
                  @ColumnInfo(name="log") val log: String
)