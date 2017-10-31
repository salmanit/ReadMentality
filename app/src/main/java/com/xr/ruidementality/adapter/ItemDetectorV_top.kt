package com.xr.ruidementality.adapter

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Sage on 2017/9/30.
 * Description:
 */
class ItemDetectorV_top(var top:Int):RecyclerView.ItemDecoration(){


    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect.top=top
    }
}