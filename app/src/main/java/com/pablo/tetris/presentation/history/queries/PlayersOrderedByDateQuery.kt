package com.pablo.tetris.presentation.history.queries

import com.pablo.tetris.presentation.history.model.HistoryViewModel

class PlayersOrderedByDateQuery(val viewModel: HistoryViewModel) : TemplateQuery() {
    override suspend fun getResult() =
        viewModel.query { repo -> repo.getPlayersOrderedByDate() }
}