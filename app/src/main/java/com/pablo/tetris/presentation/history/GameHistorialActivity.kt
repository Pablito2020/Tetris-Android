package com.pablo.tetris.presentation.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pablo.tetris.HistorialFragment
import com.pablo.tetris.R

class GameHistorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_historial)
        val history = HistorialFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentHistorial, history)
            commit()
        }
    }

}