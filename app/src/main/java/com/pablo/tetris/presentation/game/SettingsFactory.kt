package com.pablo.tetris.presentation.game

import GameFacade
import android.content.Context
import android.content.Intent
import com.pablo.tetris.domain.game.BlockGeneratorFactory
import com.pablo.tetris.presentation.common.GAME_INFORMATION
import com.pablo.tetris.presentation.game.grid.style.Style
import com.pablo.tetris.presentation.game.grid.style.StyleCreator
import com.pablo.tetris.presentation.game.grid.style.StyleFactory
import com.pablo.tetris.presentation.settings.SettingsData

object SettingsFactory {

    private lateinit var data: SettingsData

    fun fromIntent(intent: Intent) {
        data = intent.getSerializableExtra(GAME_INFORMATION) as SettingsData
    }

    fun getFacade(): GameFacade {
        val generator = BlockGeneratorFactory.getGenerator(data.level)
        return GameFacade(blockGenerator = generator, ghost = data.isGhostBlock)
    }

    fun getStyle(context: Context): StyleCreator {
        return StyleFactory.getStyleCreator(Style.values()[data.themeIndex], context)
    }

    fun hasMusic() = data.hasMusic

}