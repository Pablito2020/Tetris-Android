package com.pablo.tetris.presentation.history.view

import android.view.View
import android.widget.AdapterView
import com.pablo.tetris.presentation.history.model.HistoryViewModel
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByDateQuery
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByPointsQuery
import com.pablo.tetris.presentation.history.queries.Query

class Spinner(private val viewModel: HistoryViewModel) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val action: Query = ActionFactory.getAction(viewModel, p2)
        viewModel.executeQuery(action)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}

object ActionFactory {
    fun getAction(viewModel: HistoryViewModel, index: Int) = when (index) {
        0 -> PlayersOrderedByPointsQuery(viewModel)
        1 -> PlayersOrderedByDateQuery(viewModel)
        else -> throw IllegalArgumentException("Invalid index")
    }
}