package com.rickyslash.travis.helper

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spacing: Int, val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val spacingInDp = context.convertPixelsToDp(spacing).toInt()

        outRect.apply {
            left = spacingInDp
            right = spacingInDp
        }
    }

    private fun Context.convertPixelsToDp(pixels: Int): Float {
        val scale = resources.displayMetrics.density
        return pixels / scale
    }
}