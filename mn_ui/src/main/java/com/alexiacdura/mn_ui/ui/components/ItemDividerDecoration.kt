package com.alexiacdura.mn_ui.ui.components

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class ItemDividerDecoration(
    private val width: Int = 0,
    private val height: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildLayoutPosition(view)
        val childCount = state.itemCount

        outRect.left = width
        outRect.top = height

        if (position == childCount - 1) {
            outRect.right = width
            outRect.bottom = height
        }
    }
}