package com.pablo.tetris.presentation.game

import GameFacade
import android.content.Context
import android.content.Intent
import com.pablo.tetris.R
import com.pablo.tetris.domain.game.blocks.BlockGeneratorFactory
import com.pablo.tetris.domain.game.speed.SpeedFactory
import com.pablo.tetris.infra.logs.LoggerGetter
import com.pablo.tetris.presentation.common.GAME_INFORMATION
import com.pablo.tetris.presentation.common.UiText
import com.pablo.tetris.presentation.game.grid.style.Style
import com.pablo.tetris.presentation.game.grid.style.StyleCreator
import com.pablo.tetris.presentation.game.grid.style.StyleFactory
import com.pablo.tetris.presentation.settings.SettingsData
import java.io.Serializable

class SettingsFactory: Serializable {

    private var data: SettingsData? = null

    fun fromIntent(intent: Intent) {
        if (data == null) {
            data = intent.getSerializableExtra(GAME_INFORMATION) as SettingsData
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

    fun logData() {
        if (data != null) {
            LoggerGetter.get().add(
                UiText.ResourceString(R.string.player_name_log, data!!.name),
            )
            LoggerGetter.get().add(
                UiText.ResourceString(R.string.level_selected_log, data!!.level.name.lowercase())
            )
            LoggerGetter.get().add(
                UiText.ResourceString(R.string.ghost_mode_log, data!!.isGhostBlock)
            )
            LoggerGetter.get().add(
                UiText.ResourceString(R.string.music_mode_log, data!!.hasMusic)
            )
            LoggerGetter.get()
                .add(
                    UiText.ResourceString(
                        R.string.theme_log,
                        Style.values()[data!!.themeIndex].name.lowercase()
                    )
                )
        }
    }

}