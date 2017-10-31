package com.xr.ruidementality.fragment.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.xr.ruidementality.R
import com.xr.ruidementality.fragment.FragmentBase
import com.xr.ruidementality.fragment.search.FragmentSearch
import com.xr.ruidementality.fragment.video.FragmentVideoSort
import com.xr.ruidementality.ui.ActivityHome
import kotlinx.android.synthetic.main.fragment_audio.*
import kotlinx.android.synthetic.main.layout_toolbar_home_tab.*

/**
 * Created by Sage on 2017/9/29.
 * Description:视频总页
 */
class FragmentVideo : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_audio
    }

    lateinit var titles: Array<String>
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addPaddingMatchStateBar(layout_toolbar)
        titles = resources.getStringArray(R.array.audio_types)
        iv_left.setOnClickListener {
            (activity as ActivityHome).addFragment(FragmentTypeChoose(2),1)
        }

        iv_right.setOnClickListener {
            addFragment(FragmentSearch())
        }

        vp_audio.apply {
            adapter = object : FragmentPagerAdapter(childFragmentManager) {
                override fun getItem(position: Int): Fragment {
                    return FragmentVideoSort().createType(position + 1)
                }

                override fun getCount(): Int {
                    return titles.size
                }

                override fun getPageTitle(position: Int): CharSequence {
                    return titles[position]
                }
            }
        }
        tab_audio.setupWithViewPager(vp_audio)
    }
}