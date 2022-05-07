package com.pablo.tetris.presentation

import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.view.children

/**
 * Gets all the buttons recursively from a ViewGroup
 */
fun ViewGroup.getButtons(): List<Button> =
    getButtonsRecursive(this) { it is Button }.map { it as Button }

internal fun getButtonsRecursive(viewGroup: ViewGroup, strategy: (Any) -> Boolean): List<Any> {
    val result = ArrayList<Any>()
    for (view in viewGroup.children) {
        if (view is ViewGroup)
            result.addAll(getButtonsRecursive(view, strategy))
        else if (strategy(view))
            result.add(view)
    }
    return result
}