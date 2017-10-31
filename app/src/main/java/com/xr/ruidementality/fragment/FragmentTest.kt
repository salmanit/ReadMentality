package com.xr.ruidementality.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import com.xr.ruidementality.R
import com.xr.ruidementality.custom.NormalHandle
import com.xr.ruidementality.fragment.live.FragmentLiveLists
import kotlinx.android.synthetic.main.fragment_test.*
import java.lang.ref.SoftReference
import java.lang.reflect.Field

/**
 * Created by Sage on 2017/10/25.
 * Description:
 */
class FragmentTest : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_test
    }

    var titles = arrayListOf("标题", "标题标题饿","标题标题")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vp_test.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return FragmentLiveLists()
            }

            override fun getCount(): Int {
                return titles.size
            }

            override fun getPageTitle(position: Int): CharSequence {
                return titles[position]
            }
        }
        tab_layout.setupWithViewPager(vp_test)
//        tab_layout1.setupWithViewPager(vp_test)
        tab_layout2.addTab(tab_layout2.newTab().setCustomView(R.layout.layout_custom_tab_layout))
        tab_layout2.addTab(tab_layout2.newTab().setCustomView(R.layout.layout_custom_tab_layout))
        tab_layout2.addTab(tab_layout2.newTab().setCustomView(R.layout.layout_custom_tab_layout))
        tab_layout2.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                tab_layout2.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                changeIndicator(tab_layout2)
            }
        })


        NormalHandle.test()
        NormalHandle.test()
        NormalHandle.test()

    }

    val factor=1.1f
    fun changeIndicator(tab: TabLayout) {
        if(tab.tabCount==0){
            return
        }
        val tabLayout:Class<*> = tab.javaClass
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
            ll_tab = tabStrip.get(tab) as LinearLayout
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        /**每个tab的宽，总宽度除以2*/
        val widthTab = tab.width / tab.tabCount
        for (i in 0..ll_tab.childCount - 1) {
            val child = ll_tab.getChildAt(i)
            child.setPadding(0, 0, 0, 0)
            try {
                val tv = (child as LinearLayout).getChildAt(1) as TextView
                var margin = ((widthTab - tv.width * factor) / 2).toInt()
                println("==i==" + i + "==widthTab=" + widthTab + "==child w=" + tv.width + "==margin=" + margin)
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