package com.pablo.tetris.presentation.settings

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.databinding.ActivitySettingsBinding
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import kotlinx.coroutines.flow.collect

class SettingsActivity : HideStatusBarActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SettingsModel::class.java)
        val context: Context = this
        lifecycleScope.launchWhenCreated {
            viewModel.results.collect {
                binding.editTextTextPersonName.error = it.nameError
                if (it.nameError == null)
                    TODO("Start activity here")
            }
        }
        binding.editTextTextPersonName.addTextChangedListener { text ->
            viewModel.update(DataValue.Name(text.toString()))
        }
        binding.CheckBoxGhostBlock.setOnClickListener {
            viewModel.update(DataValue.HasGhost(binding.CheckBoxGhostBlock.isChecked))
        }
        binding.MusicCheckbox.setOnClickListener {
            viewModel.update(DataValue.HasMusic(binding.MusicCheckbox.isChecked))
        }
        binding.StartButton.setOnClickListener {
            viewModel.collect()
        }
    }

}