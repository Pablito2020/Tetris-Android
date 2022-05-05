package com.pablo.tetris.presentation.finished.sendmail

data class EmailData(
    val destinationEmail: String,
    val subject: String,
    val text: String
)
