package com.alexiacdura.mn_ui.core.binding

import androidx.databinding.BaseObservable
import kotlin.reflect.KProperty

/**
 * Delegate class that can be used to automatically notify of changes in a [BaseObservable]'s properties
 */
class BindablePropertyDelegate<in R : BaseObservable, T : Any>(private var value: T, private val bindingId: Int) {

    operator fun getValue(receiver: R, property: KProperty<*>): T = value

    operator fun setValue(receiver: R, property: KProperty<*>, value: T) {
        this.value = value
        receiver.notifyPropertyChanged(bindingId)
    }
}

fun <R : BaseObservable, T : Any> bindableProperty(value: T, bindingId: Int): BindablePropertyDelegate<R, T> {
    return BindablePropertyDelegate(value, bindingId)
}