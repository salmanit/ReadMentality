package com.xr.ruidementality.widget

import android.content.Context
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import java.lang.reflect.Field

/**
 * Created by Sage on 2017/10/25.
 * Description:此控件只适用于 app:tabMode="fixed" ，因为如果是scroll的话，
 * 游标线本来就和文字宽度一样，就没必要用这个了
 */
class TabLayoutIndicatorShort : TabLayout {
    constructor(context: Context?) : super(context) {
        initSomeThing()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initSomeThing()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initSomeThing()
    }


    private fun initSomeThing() {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                changeIndicator()
            }
        })

    }

    val factor = 1.1f
    fun changeIndicator() {
        if(tabCount==0){
            return
        }
        val tabLayout:Class<*> = javaClass.superclass
        var tabStrip: Field? = null
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip")
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        tabStrip!!.isAccessible = true
        var ll_tab: LinearLayout? = null
        try {
            ll_tab = tabStrip.get(this) as LinearLayout
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        /**每个tab的宽，总宽度除以2*/
        val widthTab = width / tabCount
        for (i in 0..ll_tab.childCount - 1) {
            val child = ll_tab.getChildAt(i)
            child.setPadding(0, 0, 0, 0)
            try {
                val tv = (child as LinearLayout).getChildAt(1) as TextView
                var margin = ((widthTab - tv.width * factor) / 2).toInt()
                println("i==" + i + "==widthTab=" + widthTab + "==child w=" + tv.width + "==margin=" + margin)
                if (margin < 0) {
                    margin = 0
                }
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, tv.width.toFloat())
                params.leftMargin = margin
                params.rightMargin = margin
                child.setLayoutParams(params)
                continue
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}