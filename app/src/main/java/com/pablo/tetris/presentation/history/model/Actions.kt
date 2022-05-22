package com.pablo.tetris.presentation.history.model

import com.pablo.tetris.presentation.history.commands.DeletePlayerCommand
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByDateQuery
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByPointsQuery

sealed class Action {
    sealed class Query(val query: com.pablo.tetris.presentation.history.queries.Query): Action() {

        data class SortByPoints(
            val viewModel: HistoryViewModel,
        ) : Query(PlayersOrderedByPointsQuery(viewModel))

        data class SortByDate(
            val viewModel: HistoryViewModel,
        ) : Query(PlayersOrderedByDateQuery(viewModel))

    }

    sealed class Command(val command: com.pablo.tetris.presentation.history.commands.Command): Action() {

        data class DeletePlayer(
            val viewModel: HistoryViewModel,
        ) : Command(DeletePlayerCommand(viewModel))

    }

}

