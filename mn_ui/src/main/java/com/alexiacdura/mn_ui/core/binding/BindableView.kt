package com.alexiacdura.mn_ui.core.binding

interface BindableView<in T> {
    fun bind(viewModel: T)
}