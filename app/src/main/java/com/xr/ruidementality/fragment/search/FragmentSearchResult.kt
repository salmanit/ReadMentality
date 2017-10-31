package com.xr.ruidementality.fragment.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.xr.ruidementality.R
import com.xr.ruidementality.fragment.FragmentBase
import kotlinx.android.synthetic.main.fragment_search_result.*

/**
 * Created by Sage on 2017/10/17.
 * Description:
 */
class FragmentSearchResult : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_search_result
    }

    var titles= arrayListOf("音频","视频")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vp_result.apply {
            adapter=object :FragmentPagerAdapter(childFragmentManager){
                override fun getItem(position: Int): Fragment {
                    when(position){
                        0->{
                            return FragmentSearchAudio().apply { searchKey=key2 }
                        }
                        1->{
                            return FragmentSearchVideo().apply { searchKey=key2 }
                        }
                        else ->{
                            return Fragment()
                        }
                    }

                }

                override fun getCount(): Int {
                   return titles.size
                }

                override fun getPageTitle(position: Int): CharSequence {
                    return titles[position]
                }
            }
        }
        tab_layout.setupWithViewPager(vp_result)
    }

    var key2=""
}