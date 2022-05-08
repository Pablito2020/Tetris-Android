package com.pablo.tetris.presentation.game

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.pablo.tetris.R

enum class State {
    PLAY,
    PAUSE
}

class PlayPauseView : AppCompatImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val playToPauseDrawable: AnimatedVectorDrawableCompat? =
        AnimatedVectorDrawableCompat.create(context, R.drawable.play_to_pause)
    private val pauseToPlayDrawable: AnimatedVectorDrawableCompat? =
        AnimatedVectorDrawableCompat.create(context, R.drawable.pause_to_play)
    private val fadeOutAnimation: Animation =
        AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
    private val fadeInAnimation: Animation =
        AnimationUtils.loadAnimation(context, android.R.anim.fade_in)

    fun setState(state: State) {
        when (state) {
            State.PLAY -> switchToPause()
            State.PAUSE -> switchToPlay()
        }
    }

    private fun switchToPause() {
        setImageDrawable(playToPauseDrawable)
        playToPauseDrawable!!.start()
    }

    private fun switchToPlay() {
        setImageDrawable(pauseToPlayDrawable)
        pauseToPlayDrawable!!.start()
    }

    fun fadeOut() {
        startAnimation(fadeOutAnimation)
        fadeOutAnimation.fillAfter = true
    }

    fun fadeIn() {
        startAnimation(fadeInAnimation)
        fadeInAnimation.fillAfter = true
    }

}