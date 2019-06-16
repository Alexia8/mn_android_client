package com.alexiacdura.mn_ui.ui.adapters.any

import androidx.recyclerview.widget.DiffUtil

abstract class AnyItemListCallback<T : AnyItem>(
    protected val oldList: List<T>,
    protected val newList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }
}