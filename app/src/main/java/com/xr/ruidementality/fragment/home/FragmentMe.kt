package com.xr.ruidementality.fragment.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ItemDetectorForMe
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.bean.UserInfo
import com.xr.ruidementality.fragment.FragmentBase
import com.xr.ruidementality.fragment.FragmentMyHistory
import com.xr.ruidementality.fragment.FragmentTest
import com.xr.ruidementality.fragment.money.FragmentMyMoney
import com.xr.ruidementality.fragment.setting.FragmentSetting
import com.xr.ruidementality.fragment.setting.FragmentUserInfo
import com.xr.ruidementality.ui.ActivityHome
import com.xr.ruidementality.ui.ActivityLogin
import com.xr.ruidementality.util.SpHelper
import com.xr.ruidementality.util.UtilThis
import kotlinx.android.synthetic.main.fragment_me.*
import org.simple.eventbus.Subscriber

/**
 * Created by Sage on 2017/9/29.
 * Description:我的页面
 */
class FragmentMe : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_me
    }

    lateinit var items: Array<String>
    var itemLogos = arrayListOf(R.mipmap.rd_me_item_history, R.mipmap.rd_me_item_ruidian, R.mipmap.rd_me_item_msg,
            R.mipmap.rd_me_item_collection, R.mipmap.rd_me_item_invite, R.mipmap.rd_me_item_discount
            , R.mipmap.rd_me_item_join, R.mipmap.rd_me_item_advice, R.mipmap.rd_me_item_setting)

    lateinit var headView: View
    lateinit var holderHead: ViewHolderRv
    lateinit var userInfo: UserInfo
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userInfo = SpHelper.getUserInfo()
        items = resources.getStringArray(R.array.me_items)
        rv_me.layoutManager = LinearLayoutManager(activity)
        rv_me.addItemDecoration(ItemDetectorForMe())//如果增加或者删除item这里需要做对应的修改
        var adapterMe = object : AdapterRv<String>(items) {
            override fun layoutId(viewType: Int): Int {
                return R.layout.item_fragment_me
            }

            override fun handleData(holder: ViewHolderRv, position: Int, t: String) {
                holder.setText(R.id.tv_item_name, t)
                holder.setImageResource(R.id.iv_item_logo, itemLogos[position])
                holder.setText(R.id.tv_money, userInfo.userMoney)
                holder.setViewVisibleGone(R.id.tv_money, position == 1)
                holder.itemView.setOnClickListener {
                    handleItemClick(position)
                }
            }
        }
        headView = LayoutInflater.from(activity).inflate(R.layout.head_item_me, rv_me, false)
        holderHead = ViewHolderRv.Companion.createInstance(headView, rv_me)
        adapterMe.addHeader(headView)
        rv_me.adapter = adapterMe

        resetUserInfo(false)
    }

    override fun eventBusEnable(): Boolean {
        return true
    }

    @Subscriber(tag = "loginStatusChange")
    fun resetUserInfo(login: Boolean) {
        var userInfo = SpHelper.getUserInfo()
        if (TextUtils.isEmpty(userInfo.id)) {
            //未登陆
            holderHead.setText(R.id.tv_name, R.string.rd_me_not_login)
            holderHead.setText(R.id.tv_toast, R.string.rd_me_not_login_toast)
            holderHead.setImageResource(R.id.iv_head, R.mipmap.rd_head_failed)
        } else {
            holderHead.setText(R.id.tv_name, userInfo.getShowName())
            holderHead.setText(R.id.tv_toast, "")
            holderHead.setImageUrl(R.id.iv_head, userInfo.headerimgUrl, R.mipmap.rd_head_failed)
        }
        holderHead.itemView.setOnClickListener {
            if (TextUtils.isEmpty(userInfo.id)) {
                startActivity(Intent(activity, ActivityLogin::class.java))
            } else {
                (activity as ActivityHome).addFragment(FragmentUserInfo(), 1)
            }
        }
        holderHead.getView<TextView>(R.id.tv_subscription).apply {
            text = "1\n我的订阅"
            setOnClickListener {

            }
        }
        holderHead.getView<TextView>(R.id.tv_download).apply {
            text = "1\n我的下载"
            setOnClickListener {

            }
        }
        holderHead.getView<TextView>(R.id.tv_purchased).apply {
            text = "1\n我的已购"
            setOnClickListener {

            }
        }
    }

    fun handleItemClick(position: Int) {
        when (position) {
            0 -> {
                //历史收听
                addFragment(FragmentMyHistory())
            }
            1 -> {
                //我的瑞点
                addFragment(FragmentMyMoney())
            }
            2 -> {

                addFragment(FragmentTest())
            }
            3 -> {

            }
            4 -> {

            }
            5 -> {

            }
            6 -> {

            }
            7 -> {

            }
            8 -> {
                addFragment(FragmentSetting())
            }
        }
    }


}