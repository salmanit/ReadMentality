package com.xr.ruidementality.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.ImageView
import com.xr.ruidementality.R
import com.xr.ruidementality.fragment.home.FragmentAudio
import com.xr.ruidementality.fragment.home.FragmentFirst
import com.xr.ruidementality.fragment.home.FragmentMe
import com.xr.ruidementality.fragment.home.FragmentVideo
import com.xr.ruidementality.fragment.live.FragmentLiveLists
import com.xr.ruidementality.fragment.player.FragmentPlayerHome
import com.xr.ruidementality.widget.DaySignManager
import kotlinx.android.synthetic.main.activity_home.*
import org.simple.eventbus.EventBus
import kotlin.reflect.KClass

class ActivityHome : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var saveIndex = savedInstanceState?.getInt("lastIndex", -1) ?: -1
        tabs.clear()
        tabs.add(iv1)
        tabs.add(iv2)
        tabs.add(iv3)
        tabs.add(iv4)

        layout_1.setOnClickListener {
            changeTabState(0)
        }
        layout_top.setOnClickListener {
            EventBus.getDefault().post("","FragmentFirstGoToTop")
        }
        layout_2.setOnClickListener {
            changeTabState(1)
        }
        layout_3.setOnClickListener {
            changeTabState(2)
        }
        layout_4.setOnClickListener {
            changeTabState(3)
        }
        iv_player_cover.setOnClickListener {
            addFragmentTotal(FragmentPlayerHome(), 2)
        }
        if (saveIndex != -1) {
            changeTabState(saveIndex)
        } else {
            layout_1.performClick()
        }

    }
    var showTop=false
    /**显示首页回到顶部按钮或者隐藏*/
    fun showFirstTopOrHidden(show:Boolean){
        if(show){
            layout_top.visibility=View.VISIBLE
            ViewCompat.setTranslationY(layout_top,0f)
        }else{
            ViewCompat.setTranslationY(layout_top,layout_top.height.toFloat())
            layout_top.visibility=View.INVISIBLE
        }
        showTop=show
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("lastIndex", lastIndex)
    }

    var tabs = ArrayList<ImageView>()
    var picsUnchecked = arrayListOf(R.mipmap.rd_tab_first, R.mipmap.rd_tab_audio,
            R.mipmap.rd_tab_video, R.mipmap.rd_tab_me)
    var picsChecked = arrayListOf(R.mipmap.rd_tab_first_checked, R.mipmap.rd_tab_audio_checked,
            R.mipmap.rd_tab_video_checked, R.mipmap.rd_tab_me_checked)
    var lastIndex = -1
    fun changeTabState(index: Int) {
        if (lastIndex == index) {
            return
        }
        lastIndex = index
        for (i in 0..3) {
            tabs[i].setImageResource(if (i != index) picsUnchecked[i] else picsChecked[i])
        }
        if(index==0){
            showFirstTopOrHidden(showTop)
        }else{
            layout_top.visibility=View.INVISIBLE
        }
        showWhich(index)
//        layout_root.fitsSystemWindows = index != 3

    }

    var fragmentClasses = arrayListOf(FragmentFirst::class.java, FragmentAudio::class.java
            , FragmentVideo::class.java, FragmentMe::class.java)

    fun showWhich(index: Int) {
        var transaction = supportFragmentManager.beginTransaction()
        (0..3).forEach {
            var fragment = getFragmentByName(fragmentClasses[it])
            if (index == it) {
                if (fragment == null) {
                    transaction.add(R.id.layout_tab, getNewFragment(index), fragmentClasses[it].simpleName)
                } else {
                    transaction.show(fragment)
                }
            } else {
                if (fragment != null) {
                    transaction.hide(fragment)
                }
            }
        }
        transaction.commitAllowingStateLoss()
    }

    fun getNewFragment(index: Int): Fragment {
        when (index) {
            0 -> return FragmentFirst()
            1 -> return FragmentAudio()
            2 -> return FragmentVideo()
            3 -> return FragmentMe()
            else -> return Fragment()
        }
    }

    fun <T : Fragment> getFragmentByName(cla: Class<T>): T? {
        var fragment = supportFragmentManager.findFragmentByTag(cla.simpleName)
        if (fragment != null) {
            return fragment as T
        } else {
            return null
        }
    }


    //动画是从底部到顶部
    fun addFragment(fragment: Fragment) {
        addFragment(fragment, 1)
    }

    fun addFragment(fragment: Fragment, type: Int) {
        addFragmentBase(fragment, R.id.layout_container, type)
    }

    fun addFragmentTotal(fragment: Fragment, type: Int) {
        addFragmentBase(fragment, R.id.layout_root, type)
    }

    fun addFragmentBase(fragment: Fragment, layout_id: Int, type: Int) {
        var transaction = supportFragmentManager.beginTransaction()
        when (type) {
            1 -> {
                transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_in, R.anim.back_left_in, R.anim.back_right_out)
            }
            2 -> {
                transaction.setCustomAnimations(R.anim.anim_in, R.anim.anim_in, R.anim.y_anim_in, R.anim.y_anim_out)
            }
        }
        transaction.add(layout_id, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName).commitAllowingStateLoss()
    }

    //1是音频，2是视频，3是直播
    fun goWhere(mediaType: Int) {
        when (mediaType) {
            1 -> {
                layout_2.performClick()
            }
            2 -> {
                layout_3.performClick()
            }
            3 -> {
                addFragment(FragmentLiveLists(), 1)
            }
        }
    }


    override fun onBackPressed() {
        if (DaySignManager.onbackPress()) {
            return
        }
        super.onBackPressed()
    }
}
