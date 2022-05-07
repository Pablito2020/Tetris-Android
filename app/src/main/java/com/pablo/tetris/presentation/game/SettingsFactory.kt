package com.pablo.tetris.presentation.game

import GameFacade
import android.content.Context
import android.content.Intent
import com.pablo.tetris.domain.game.blocks.BlockGeneratorFactory
import com.pablo.tetris.domain.game.speed.SpeedFactory
import com.pablo.tetris.infra.logs.LoggerGetter
import com.pablo.tetris.presentation.common.GAME_INFORMATION
import com.pablo.tetris.presentation.game.grid.style.Style
import com.pablo.tetris.presentation.game.grid.style.StyleCreator
import com.pablo.tetris.presentation.game.grid.style.StyleFactory
import com.pablo.tetris.presentation.settings.SettingsData

class SettingsFactory {

    private var data: SettingsData? = null

    fun fromIntent(intent: Intent) {
        if (data == null) {
            data = intent.getSerializableExtra(GAME_INFORMATION) as SettingsData
            LoggerGetter.get().add("Name of the player is: ${data!!.name}")
            LoggerGetter.get().add("Level selected is: ${data!!.level.name.lowercase()}")
            LoggerGetter.get().add("Ghost mode is enabled? ${data!!.isGhostBlock}")
            LoggerGetter.get().add("Music is enabled? ${data!!.hasMusic}")
            LoggerGetter.get()
                .add("Playing with theme: ${Style.values()[data!!.themeIndex].name.lowercase()}")
        }
    }

    fun getFacade(): GameFacade {
        val generator = BlockGeneratorFactory.getGenerator(data!!.level)
        return GameFacade(blockGenerator = generator, ghost = data!!.isGhostBlock)
    }

    fun getStyle(context: Context): StyleCreator {
        return StyleFactory.getStyleCreator(Style.values()[data!!.themeIndex], context)
    }

    fun getSpeedStrategy() = SpeedFactory.get(data!!.level)

    fun hasMusic() = data!!.hasMusic

}