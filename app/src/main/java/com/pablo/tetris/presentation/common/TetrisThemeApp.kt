package com.pablo.tetris.presentation.common

import android.app.Application
import com.google.android.material.color.DynamicColors

class TetrisThemeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

}