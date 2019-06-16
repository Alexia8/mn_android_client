package com.alexiacdura.mn_ui.core.utils

import android.content.res.TypedArray

inline fun <R> TypedArray.use(block: (TypedArray) -> R): R {
    return block(this).also {
        recycle()
    }
}