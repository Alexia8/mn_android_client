package com.alexiacdura.mn_ui.core.binding

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Delegate class used to inflate layout views by removing the inflation logic from the target view.
 */
class ViewBindingDelegate<out T : ViewDataBinding>(@LayoutRes private val resId: Int) : ReadOnlyProperty<ViewGroup, T> {

    private var binding: T? = null

    override fun getValue(thisRef: ViewGroup, property: KProperty<*>): T {
        if (binding == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(thisRef.context), resId, thisRef, true)
        }

        return binding as T
    }
}

fun <T : ViewDataBinding> viewBinding(@LayoutRes resId: Int): ViewBindingDelegate<T> {
    return ViewBindingDelegate(resId)
}