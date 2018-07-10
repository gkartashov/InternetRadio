package com.jg.internetradio.ui.misc

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View

fun marginDecorator(context: Context?) = object : RecyclerView.ItemDecoration() {
    val padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8.0f, context?.resources?.displayMetrics).toInt()

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val itemPosition = parent?.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }
        if (itemPosition == 0) {
            outRect?.top = padding
        }

        val adapter = parent?.adapter
        if (itemPosition == adapter?.itemCount?.minus(1)) {
            outRect?.bottom = padding
        }
    }
}