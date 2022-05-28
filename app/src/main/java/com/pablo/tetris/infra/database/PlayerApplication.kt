package com.pablo.tetris.infra.database

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.color.DynamicColors

class PlayerApplication : Application(), ViewModelStoreOwner {
    val database by lazy { PlayerRoomDatabase.getDatabase(this) }
    val repository by lazy { PlayerRepository(database.playerDao()) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }

}