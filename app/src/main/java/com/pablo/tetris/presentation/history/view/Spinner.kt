package com.pablo.tetris.presentation.history.view

import android.view.View
import android.widget.AdapterView
import com.pablo.tetris.presentation.history.model.Action
import com.pablo.tetris.presentation.history.model.HistoryViewModel

class Spinner(private val viewModel: HistoryViewModel) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val action = ActionFactory.getAction(viewModel, p2)
        viewModel.executeAction(action)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}

object ActionFactory {
    fun getAction(viewModel: HistoryViewModel, index: Int) = when (index) {
        0 -> Action.Query.SortByPoints(viewModel)
        1 -> Action.Query.SortByDate(viewModel)
        2 -> Action.Command.DeletePlayer(viewModel)
        else -> throw IllegalArgumentException("Invalid index")
    }
}