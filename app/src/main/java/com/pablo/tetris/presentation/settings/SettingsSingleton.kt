package com.pablo.tetris.presentation.settings

import GameFacade
import android.content.Context
import androidx.preference.PreferenceManager
import com.pablo.tetris.R
import com.pablo.tetris.domain.game.Level
import com.pablo.tetris.domain.game.blocks.BlockGeneratorFactory
import com.pablo.tetris.domain.game.speed.SpeedFactory
import com.pablo.tetris.domain.game.speed.SpeedStrategy
import com.pablo.tetris.infra.logs.LoggerGetter
import com.pablo.tetris.presentation.common.UiText
import com.pablo.tetris.presentation.game.grid.style.Style
import com.pablo.tetris.presentation.game.grid.style.StyleCreator
import com.pablo.tetris.presentation.game.grid.style.StyleFactory
import java.io.Serializable

object SettingsSingleton : Serializable {

    fun getSettingsData(context: Context): SettingsData {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val name = preferences.getString("name", "default user")
        val isGhost = preferences.getBoolean("ghost_block", true)
        val hasMusic = preferences.getBoolean("music", true)
        val difficultyString = preferences.getString("difficulty", "")
        val themeString = preferences.getString("theme", "")
        val level = try {
            Level.valueOf(difficultyString!!)
        } catch (IllegalArgumentException: Exception) {
            Level.MEDIUM
        }
        val theme = try {
            Style.valueOf(themeString!!)
        } catch (IllegalArgumentException: Exception) {
            Style.NEON
        }
        return SettingsData(
            name = name!!,
            isGhostBlock = isGhost,
            hasMusic = hasMusic,
            level = level,
            style = theme
        )
    }

    fun getFacade(context: Context): GameFacade {
        val settings = getSettingsData(context)
        val generator = BlockGeneratorFactory.getGenerator(settings.level)
        return GameFacade(blockGenerator = generator, ghost = settings.isGhostBlock)
    }

    fun getStyleCreator(context: Context): StyleCreator {
        val settings = getSettingsData(context)
        return StyleFactory.getStyleCreator(settings.style, context)
    }

    fun getSpeedStrategy(context: Context): SpeedStrategy {
        val settings = getSettingsData(context)
        return SpeedFactory.get(settings.level)
    }

    fun logData(context: Context) {
        val settings = getSettingsData(context)
        LoggerGetter.get().add(
            UiText.ResourceString(R.string.player_name_log, settings.name),
        )
        LoggerGetter.get().add(
            UiText.ResourceString(R.string.level_selected_log, settings.level.name)
        )
        LoggerGetter.get().add(
            UiText.ResourceString(R.string.ghost_mode_log, settings.isGhostBlock)
        )
        LoggerGetter.get().add(
            UiText.ResourceString(R.string.music_mode_log, settings.hasMusic)
        )
        LoggerGetter.get()
            .add(
                UiText.ResourceString(R.string.theme_log, settings.style.name)
            )
    }

}