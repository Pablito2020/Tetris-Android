package com.pablo.tetris.presentation.game.results

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class GameResult(
    val score: Int,
    val date: Date
) : Serializable

object DateGetter {
    private const val DATE_FORMAT = "EEEE, dd-MMM-yyyy hh-mm-ss a"

    fun getDate(date: Date): String {
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }

}
