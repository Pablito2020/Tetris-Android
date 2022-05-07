package com.pablo.tetris.presentation.game.results

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class GameResult(
    val score: String,
    val date: String
) : Serializable

object DateGetter {
    private const val DATE_FORMAT = "EEEE, dd-MMM-yyyy hh-mm-ss a"

    fun getDate(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        return formatter.format(calendar.time)
    }

}
