package com.xr.ruidementality.adapter

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Sage on 2017/10/12.
 * Description:给网格布局用的
 */
class ItemDetectorGrid(var spacing:Int,var leftRightDraw:Boolean):RecyclerView.ItemDecoration(){
     var topDraw = false//第一行顶部和最后一行底部是否要分割
     var bottomDraw = false//第一行顶部和最后一行底部是否要分割
    fun setTopBottom(topDraw: Boolean, bottomDraw: Boolean): ItemDetectorGrid {
        this.topDraw = topDraw
        this.bottomDraw = bottomDraw
        return this
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val manager = (parent.layoutManager ?: return) as? GridLayoutManager ?: return
        val gridLayoutManager = manager
        val spanCount = gridLayoutManager.spanCount
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        val count = gridLayoutManager.itemCount
        if (count == 0) {
            return
        }
        var last = count % spanCount//找出最后一行的位置
        if (last == 0) {
            last = spanCount
        }
        if (leftRightDraw) {
            outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)


        } else {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            //            if (position >= spanCount) {
            //                outRect.top = spacing; // item top
            //            }
        }
        if (topDraw) {
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
        }
        outRect.bottom = spacing // item bottom
        if (!bottomDraw && position >= count - last) {
            outRect.bottom = 0
        }
    }
}