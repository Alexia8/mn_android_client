package com.alexiacdura.mn_ui.ui.adapters.any

import androidx.recyclerview.widget.RecyclerView
import android.util.SparseArray
import android.view.ViewGroup
import com.alexiacdura.mn_ui.ui.adapters.BindingViewHolder

abstract class AnyAdapter<T : AnyItem> : RecyclerView.Adapter<BindingViewHolder<*>>() {

    private val bindingGenerators = SparseArray<AnyItem.BindingGenerator>()

    override fun getItemViewType(position: Int): Int {
        val bindingGenerator = getItem(position).bindingGenerator
        // The layout resource is a unique id for every view/viewModel type
        val viewType = bindingGenerator.layout

        if (bindingGenerators.get(viewType) == null) {
            bindingGenerators.put(viewType, bindingGenerator)
        }
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<*> {
        return bindingGenerators.get(viewType).getViewBindingViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<*>, position: Int) {
        holder.dataBinding?.apply {
            val item = getItem(position)
            holder.itemView.setTag(BindingViewHolder.MODEL_TAG, item)
            val variableDeclared = setVariable(holder.variableId, item)

            if (!variableDeclared) {
                throw IllegalArgumentException(
                    "Binding (${javaClass.simpleName}) must declare " +
                            "what the id for the variable it uses in data binding!\n Can't be: ${holder.variableId}"
                )
            }

            if (hasPendingBindings()) {
                notifyChange()
                executePendingBindings()
            }
        }
    }

    abstract fun getItem(position: Int): T
}