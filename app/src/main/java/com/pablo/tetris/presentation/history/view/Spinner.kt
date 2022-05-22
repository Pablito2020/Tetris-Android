package com.pablo.tetris.presentation.history.view

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import com.pablo.tetris.presentation.history.model.Action
import com.pablo.tetris.presentation.history.model.HistoryViewModel

class Spinner(private val viewModel: HistoryViewModel, val activity: Activity) :
    AdapterView.OnItemSelectedListener {

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val action: Action = ActionFactory.getAction(viewModel, p2, activity)
        viewModel.executeAction(action)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}

object ActionFactory {
    fun getAction(viewModel: HistoryViewModel, index: Int, activity: Activity) = when (index) {
        0 -> Action.Query.SortByPoints(viewModel)
        1 -> Action.Query.SortByDate(viewModel)
        2 -> Action.Query.SearchByName(viewModel, activity)
        3 -> Action.Command.DeletePlayer(viewModel)
        else -> throw IllegalArgumentException("Invalid index")
    }
}