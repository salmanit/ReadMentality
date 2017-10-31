package com.xr.ruidementality.adapter

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Sage on 2017/10/12.
 * Description:给网格布局用的
 * leftRightDraw 左右两边是否画间隔
 * spacing，默认的水平和垂直的间距是一样的，如果不想一样调用单独的设置方法setHVSpace设置
 */
class ItemDetectorGridHV(var spacing: Int, var leftRightDraw: Boolean) : RecyclerView.ItemDecoration() {
    var topDraw = false//第一行顶部和最后一行底部是否要分割
    var bottomDraw = false//第一行顶部和最后一行底部是否要分割
    fun setTopBottom(topDraw: Boolean, bottomDraw: Boolean): ItemDetectorGridHV {
        this.topDraw = topDraw
        this.bottomDraw = bottomDraw
        return this
    }

    var spaceH = -1;//水平防线的间隔
    var spaceV = -1;//垂直方向的间隔
    fun setHVSpace(spaceH: Int, spaceV: Int): ItemDetectorGridHV {
        this.spaceH = spaceH
        this.spaceV = spaceV
        return this
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val manager = (parent.layoutManager ?: return) as? GridLayoutManager ?: return
        if(spaceH<0||spaceV<0){
            spaceH=spacing
            spaceV=spacing
        }
        val gridLayoutManager = manager
        val spanCount = gridLayoutManager.spanCount
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        val count = gridLayoutManager.itemCount
        if (count == 0) {
            return
        }
        var row=position/spanCount
        //最后一行的索引，从0开始
        val lastRow=(count-1)/spanCount
        outRect.left=spaceV/2
        outRect.right=spaceV/2
        if(column==0){
            outRect.left=if(leftRightDraw) spaceV else 0
        }
        if(column==spanCount-1){
            outRect.right=if(leftRightDraw) spaceV else 0
        }
        outRect.left=spaceV/2
        outRect.right=spaceV/2
        if(column==0){
            outRect.left=if(leftRightDraw) spaceV else 0
        }
        if(column==spanCount-1){
            outRect.right=if(leftRightDraw) spaceV else 0
        }

        outRect.top=spaceH/2
        outRect.bottom=spaceH/2
        if(row==0){
            outRect.top = if(topDraw) spaceH else 0
        }
        if(row ==lastRow){
            outRect.bottom=if(bottomDraw) spaceH else 0
        }

    }
}