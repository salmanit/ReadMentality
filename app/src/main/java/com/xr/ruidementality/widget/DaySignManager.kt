package com.xr.ruidementality.widget

/**
 * Created by Sage on 2017/10/13.
 * Description:用来记录当前的日签页面是否全屏状态，是的话activity后退的时候得判断下，
 * 如果是全屏就退出全屏
 */
object DaySignManager {

    var daySignLayout: DaySignLayout? = null
    fun onbackPress(): Boolean {
        if (daySignLayout != null && daySignLayout!!.fullScreen) {
            daySignLayout!!.exitFullScreen()
            return true
        } else {
            return false
        }
    }
}