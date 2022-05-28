package com.pablo.tetris.presentation.history

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.pablo.tetris.presentation.history.fragments.LogHistoryFragment
import com.pablo.tetris.presentation.history.fragments.HistorialFragment
import com.pablo.tetris.R

class GameHistorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_historial)
        val hasLogFragment = (findViewById<FrameLayout>(R.id.logFragmentHistorial) != null)
        val history = HistorialFragment().apply {
            arguments = Bundle().apply {
                putBoolean(HistorialFragment.HAS_LOG_FRAGMENT, hasLogFragment)
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentHistorial, history)
            commit()
        }
        if (hasLogFragment) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.logFragmentHistorial, LogHistoryFragment())
                commit()
            }
        }
    }

}