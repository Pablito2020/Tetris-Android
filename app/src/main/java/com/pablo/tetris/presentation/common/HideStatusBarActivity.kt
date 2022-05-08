package com.pablo.tetris.presentation.common

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pablo.tetris.R
import kotlin.system.exitProcess

open class HideStatusBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        hideStatusBar()
        super.onCreate(savedInstanceState)
    }

    protected fun hideStatusBar() {
        hideNavBar()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window.setDecorFitsSystemWindows(false)
        else
            olderCompatibilityHiding()
    }

    override fun onResume() {
        super.onResume()
        hideStatusBar()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(R.string.warning)
            .setMessage(R.string.quitAppMessage)
            .setNegativeButton(R.string.cancel) { _, _ -> hideStatusBar() }
            .setPositiveButton(R.string.ok) { _, _ -> finishAffinity();exitProcess(0) }
            .create()
            .show()
    }

    private fun hideNavBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controllerCompat = WindowInsetsControllerCompat(window, window.decorView)
        controllerCompat.hide(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.navigationBars())
        controllerCompat.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    @Suppress("DEPRECATION")
    private fun olderCompatibilityHiding() {
        val decorView: View = window.decorView
        decorView.systemUiVisibility
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
        }
    }

}