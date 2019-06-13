package com.alexiacdura.mn_ui.ui.adapters

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.alexiacdura.mn_ui.R

class BindingViewHolder<T : ViewDataBinding>(itemView: View, val variableId: Int) : RecyclerView.ViewHolder(itemView) {

    val dataBinding: T? = DataBindingUtil.bind(itemView)

    companion object {
        @JvmField
        val LAYOUT_TAG = R.id.binding_viewholder_layout_id
        @JvmField
        val MODEL_TAG = R.id.binding_viewholder_content_model
    }
}