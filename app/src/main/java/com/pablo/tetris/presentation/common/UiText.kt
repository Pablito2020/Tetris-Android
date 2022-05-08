package com.pablo.tetris.presentation.common

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class PrimitiveString(val value: String) : UiText()
    class ResourceString(@StringRes val resourceId: Int, vararg val args: Any) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is PrimitiveString -> value
            is ResourceString -> {
                val newArgs = this.args.map { if (it is UiText) it.asString(context) else it }.toTypedArray()
                context.getString(resourceId, *newArgs)
            }
        }
    }

}
