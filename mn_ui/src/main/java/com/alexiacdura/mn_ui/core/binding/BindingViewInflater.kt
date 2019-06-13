package com.alexiacdura.mn_ui.core.binding

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.AttrRes
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alexiacdura.mn_ui.core.utils.StyleUtils

/**
 * Class used to inflate fragments or views while also attaching them to particular styles defined
 * by an attribute. It should be used to inflate the layout long after the fragment / view instance
 * has been created and when a container is required in the inflation process.
 */
class BindingViewInflater(
    @LayoutRes private val resId: Int,
    @AttrRes private val styleAttributeId: Int? = null
) {

    fun <T : ViewDataBinding> inflate(context: Context, container: ViewGroup?): T {
        val wrappedContext = styleAttributeId?.let {
            StyleUtils.getContextThemeWrapper(context, styleAttributeId)
        } ?: context

        return DataBindingUtil.inflate(LayoutInflater.from(wrappedContext), resId, container, false)
    }
}