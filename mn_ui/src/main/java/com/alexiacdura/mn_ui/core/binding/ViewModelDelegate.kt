package com.alexiacdura.mn_ui.core.binding

import kotlin.reflect.KProperty

/**
 * Delegate class used to easily bind a view model to a [BindableView] view
 */
class ViewModelDelegate<T>(private var viewModel: T) {

    operator fun getValue(receiver: BindableView<T>, property: KProperty<*>): T = viewModel

    operator fun setValue(receiver: BindableView<T>, property: KProperty<*>, newValue: T) {
        viewModel = newValue

        receiver.bind(newValue)
    }
}

fun <T> viewModel(viewModel: T): ViewModelDelegate<T> {
    return ViewModelDelegate(viewModel)
}