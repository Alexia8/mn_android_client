package com.alexiacdura.mn_ui.core.utils.resources

import android.content.Context
import androidx.annotation.AttrRes
import com.alexiacdura.mn_ui.core.utils.StyleUtils

fun Context.wrapContextFrom(@AttrRes attrId: Int): Context {
    return StyleUtils.getContextThemeWrapper(this, attrId)
}