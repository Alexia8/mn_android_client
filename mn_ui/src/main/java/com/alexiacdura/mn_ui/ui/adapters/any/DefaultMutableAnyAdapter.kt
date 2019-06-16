package com.alexiacdura.mn_ui.ui.adapters.any

import androidx.recyclerview.widget.DiffUtil
import java.util.*

class DefaultMutableAnyAdapter<T : AnyItem>(
    private val items: MutableList<T> = mutableListOf(),
    private val diffCallbackBuilder: ((oldItems: List<T>, newItems: List<T>) -> AnyItemListCallback<T>)? = null
) : MutableAnyAdapter<T>() {

    override fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    override fun addItem(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    override fun addItems(items: Collection<T>) {
        val initialItemCount = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(initialItemCount, initialItemCount + items.size - 1)
    }

    override fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun moveItem(fromPosition: Int, toPosition: Int) {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun updateItems(items: Collection<T>) {
        diffCallbackBuilder?.let {
            val diff = DiffUtil.calculateDiff(it.invoke(this@DefaultMutableAnyAdapter.items, items.toList()))
            diff.dispatchUpdatesTo(this@DefaultMutableAnyAdapter)
        } ?: notifyDataSetChanged()

        this.items.apply {
            clear()
            addAll(items)
        }
    }

    override fun getItems(): Collection<T> = items

    override fun getItem(position: Int) = items[position]

    override fun getItemCount() = items.size
}