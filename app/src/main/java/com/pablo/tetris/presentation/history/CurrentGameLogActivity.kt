package com.pablo.tetris.presentation.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pablo.tetris.R
import com.pablo.tetris.databinding.ActivityCurrentGameLogBinding
import com.pablo.tetris.presentation.history.fragments.LogHistoryFragment

class CurrentGameLogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentGameLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentGameLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val x = LogHistoryFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.current_game_log_frame_layout, x)
            commit()
        }
    }

}