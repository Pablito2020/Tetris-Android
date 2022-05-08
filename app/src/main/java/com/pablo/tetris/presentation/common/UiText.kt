package com.pablo.tetris.presentation.common

import android.content.Context

sealed class UiText {
    data class PrimitiveString(val value: String) : UiText()
    data class ResourceString(val resourceId: Int, var args: Any? = null) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is PrimitiveString -> value
            is ResourceString -> {
                if (this.args == null)
                    context.getString(resourceId)
                else
                    context.getString(resourceId, args)
            }
        }
    }

}
