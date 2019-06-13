package com.alexiacdura.mn_ui.ui.adapters.any

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class MutableAnyAdapter<T : AnyItem> : AnyAdapter<T>() {
    abstract fun addItem(item: T)
    abstract fun addItem(position: Int, item: T)
    abstract fun addItems(items: Collection<T>)
    abstract fun removeItem(position: Int)
    abstract fun moveItem(fromPosition: Int, toPosition: Int)
    abstract fun updateItems(items: Collection<T>)
    abstract fun getItems(): Collection<T>

    companion object {
        const val BUILDER: String = "builder"
    }
}

object MutableAnyItemsAdapter {

    @JvmStatic
    @BindingAdapter("items")
    @Suppress("UNCHECKED_CAST")
    fun bindAdapterItems(recyclerView: RecyclerView, items: List<AnyItem>?) {
        items?.let {
            val adapter = recyclerView.adapter
            if (adapter is MutableAnyAdapter<*>) {
                (adapter as MutableAnyAdapter<AnyItem>).updateItems(items)
            } else {
                throw IllegalStateException("The recycler view must be using a MutableAnyAdapter")
            }
        }
    }
}