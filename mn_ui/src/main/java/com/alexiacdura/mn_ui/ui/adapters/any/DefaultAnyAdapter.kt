package com.alexiacdura.mn_ui.ui.adapters.any

class DefaultAnyAdapter<T : AnyItem>(private val items: List<T>) : AnyAdapter<T>() {

    override fun getItem(position: Int) = items[position]
    override fun getItemCount() = items.size
}