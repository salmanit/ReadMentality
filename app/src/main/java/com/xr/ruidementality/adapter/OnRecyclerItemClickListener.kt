package com.xr.ruidementality.adapter

import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.support.v4.view.GestureDetectorCompat
import android.view.GestureDetector

/**
 * Created by Sage on 2017/9/29.
 * Description:
 */
abstract class OnRecyclerItemClickListener : RecyclerView.OnItemTouchListener {
    constructor(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        mGestureDetector = GestureDetectorCompat(recyclerView.context, ItemTouchHelperGestureListener())
    }

    private var recyclerView: RecyclerView
    private var mGestureDetector: GestureDetectorCompat
    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
        mGestureDetector.onTouchEvent(e);
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    abstract fun onItemClick(vh: RecyclerView.ViewHolder, position: Int)
    fun onItemLongClick(vh: RecyclerView.ViewHolder, position: Int) {}
    inner class ItemTouchHelperGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val child = recyclerView.findChildViewUnder(e.x, e.y)
            if (child != null) {
                val vh = recyclerView.getChildViewHolder(child)
//                child!!.onTouchEvent(e)
                onItemClick(vh, vh.getAdapterPosition())
            }
            return true
        }

        //长点击事件
        override fun onLongPress(e: MotionEvent) {
            val child = recyclerView.findChildViewUnder(e.x, e.y)
            if (child != null) {
                val vh = recyclerView.getChildViewHolder(child)
                child!!.onTouchEvent(e)
                onItemLongClick(vh, vh.getAdapterPosition())
            }
        }
    }
}