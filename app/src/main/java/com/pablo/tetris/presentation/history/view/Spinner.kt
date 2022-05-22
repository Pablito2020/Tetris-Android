package com.pablo.tetris.presentation.history.view

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import com.pablo.tetris.presentation.history.model.HistoryViewModel
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByDateQuery
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByPointsQuery
import com.pablo.tetris.presentation.history.queries.Query
import com.pablo.tetris.presentation.history.queries.SearchPlayerByName

class Spinner(private val viewModel: HistoryViewModel, val activity: Activity) :
    AdapterView.OnItemSelectedListener {

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val action: Query = ActionFactory.getAction(viewModel, p2, activity)
        viewModel.executeQuery(action)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}

object ActionFactory {
    fun getAction(viewModel: HistoryViewModel, index: Int, activity: Activity) = when (index) {
        0 -> PlayersOrderedByPointsQuery(viewModel)
        1 -> PlayersOrderedByDateQuery(viewModel)
        2 -> SearchPlayerByName(viewModel, activity)
        else -> throw IllegalArgumentException("Invalid index")
    }
}