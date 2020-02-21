package com.lkw1120.memo.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListDecoration(
    private val width: Int,
    private val height: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {

        val position: Int = parent.getChildAdapterPosition(view)
        outRect.left = width
        outRect.right = width
        outRect.top = if(position == 0) height else 0
        outRect.bottom = height
    }

}