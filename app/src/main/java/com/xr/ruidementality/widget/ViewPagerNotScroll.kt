package com.xr.ruidementality.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Sage on 2017/9/27.
 * Description:禁止viewpager 通过手势左右滑动
 */
class ViewPagerNotScroll: ViewPager {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    var noScroll =true


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if(noScroll){
            return false
        }
        return super.onTouchEvent(ev)
    }
}