package com.pablo.tetris.presentation.settings

import android.R.layout.simple_spinner_dropdown_item
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.pablo.tetris.presentation.game.grid.style.Style

class Spinner(private val viewModel: SettingsModel) : AdapterView.OnItemSelectedListener {

    object Adapter {
        fun get(context: Context) = ArrayAdapter(
            context,
            simple_spinner_dropdown_item,
            Style.values().map { it.name.lowercase() })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.update(DataValue.Theme(p2))
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}