package com.pablo.tetris.presentation.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pablo.tetris.R
import com.pablo.tetris.SettingsFragment
import com.pablo.tetris.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_settings, SettingsFragment())
            .commit()
    }

}