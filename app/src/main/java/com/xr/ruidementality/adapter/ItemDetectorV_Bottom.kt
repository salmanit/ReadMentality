package com.xr.ruidementality.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Sage on 2017/9/30.
 * Description:
 */
class ItemDetectorV_Bottom(var space: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect.bottom = space
    }

    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var lineColor = Color.parseColor("#c9c9c9")
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        var childCount = parent.childCount
        paint.color = lineColor
        (0..childCount - 1)
                .map { parent.getChildAt(it) }
                .forEach {
                    c.drawRect(parent.paddingLeft.toFloat(), it.bottom.toFloat(),
                            (parent.width - parent.paddingRight).toFloat(), (it.bottom + space).toFloat(), paint)
                }
    }
}