package com.pablo.tetris.presentation.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.databinding.ActivitySettingsBinding
import com.pablo.tetris.domain.Level
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.game.grid.style.Style
import kotlinx.coroutines.flow.collect

class SettingsActivity : HideStatusBarActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SettingsModel::class.java)
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
        binding.ThemeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            Style.values().map { it.name.lowercase() },
        )
        binding.ThemeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.update(DataValue.Theme(p2))
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.LowLevel.setOnClickListener {
            viewModel.update(DataValue.Level(Level.LOW))
        }
        binding.MediumLevelButton.setOnClickListener {
            viewModel.update(DataValue.Level(Level.MEDIUM))
        }
        binding.HighLevelButton.setOnClickListener {
            viewModel.update(DataValue.Level(Level.HIGH))
        }
    }
}