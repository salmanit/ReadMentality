package com.xr.ruidementality.fragment.player

import android.os.Bundle
import com.xr.ruidementality.R
import com.xr.ruidementality.fragment.FragmentBase
import kotlinx.android.synthetic.main.fragment_player_home.*

/**
 * Created by Sage on 2017/10/23.
 * Description:音乐播放主页面
 */
class FragmentPlayerHome : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_player_home
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iv_back.setOnClickListener {
            onBackPress()
        }
        iv_music_play.setOnClickListener {
            showToast("play")

        }
        iv_music_pre.setOnClickListener {
            showToast("iv_music_pre")
        }
        iv_music_next.setOnClickListener {
            showToast("iv_music_next")
        }
        iv_music_list.setOnClickListener {
            showToast("iv_music_list")
        }
        iv_music_down.setOnClickListener {
            showToast("iv_music_down")
        }
        iv_music_like.setOnClickListener {
            showToast("iv_music_like")
        }
        iv_music_share.setOnClickListener {
            showToast("iv_music_share")
        }
        iv_music_more.setOnClickListener {
            showToast("iv_music_more")
        }
        tv_subscription_state.setOnClickListener {
            showToast("state")
        }
    }

}