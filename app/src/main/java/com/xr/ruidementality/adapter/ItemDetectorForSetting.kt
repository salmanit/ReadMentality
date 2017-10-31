package com.xr.ruidementality.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by Sage on 2017/10/11.
 * Description:首页我的页面的分割线
 */
class ItemDetectorForSetting : RecyclerView.ItemDecoration {

    var height1 = 0//细的分割线高度
    var height2 = 0//粗的分割线高度
    var dividerColor = Color.parseColor("#e9e9e9")
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor() : super() {
        paint.color = dividerColor

    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (height1 == 0) {
            height1 = parent.context.resources.displayMetrics.density.toInt()
            height2 = height1 * 10
        }
        var position = parent.getChildAdapterPosition(view)
        if (isHeight2(position)) {
            outRect.bottom = height2
        } else {
            outRect.bottom = height1
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        var count = parent.layoutManager.itemCount
        for (position in 0..count - 1) {
            var child = parent.getChildAt(position)
            if (child != null) {
                var height = height1
                var left=height2*2
                if (isHeight2(position)) {
                    height = height2
                    left=0
                }
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                c.drawRect(left.toFloat(), top.toFloat(), child.width.toFloat(), (child.bottom + height).toFloat(), paint)
            }

        }
    }

    fun isHeight2(position:Int):Boolean{
        return position == 1 || position == 4
    }
}