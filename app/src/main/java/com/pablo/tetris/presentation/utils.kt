package com.pablo.tetris.presentation

import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children

/**
 * Gets all the buttons recursively from a ViewGroup
 */
fun ViewGroup.getButtons(): List<Button> = getButtonsRecursive(this)

internal fun getButtonsRecursive(viewGroup: ViewGroup): List<Button> {
    val result = ArrayList<Button>()
    for (view in viewGroup.children) {
        if (view is ViewGroup)
            result.addAll(getButtonsRecursive(view))
        else if (view is Button)
            result.add(view)
    }
    return result
}