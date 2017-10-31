package com.xr.ruidementality.fragment.setting

import android.os.Bundle
import com.xr.ruidementality.R
import com.xr.ruidementality.fragment.FragmentBase
import com.xr.ruidementality.util.SpHelper
import com.xr.ruidementality.util.UtilThis
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.layout_toolbar_normal.*

/**
 * Created by Sage on 2017/10/16.
 * Description:个人信息页
 */
class FragmentUserInfo : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_user_info
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iv_back.setOnClickListener {
            activity.supportFragmentManager.popBackStack()
        }
        tv_title.text = "个人信息"
        var userInfo = SpHelper.getUserInfo()
        UtilThis.loadPic(iv_head, userInfo.headerimgUrl, R.mipmap.rd_head_failed)
        layout_head.setOnClickListener {

        }

        tv_nick_name.text = userInfo.getShowName()
        layout_nick_name.setOnClickListener {

        }

        tv_phone.text = userInfo.userMobile

        layout_psw.setOnClickListener {

        }
        layout_third.setOnClickListener {

        }
    }
}