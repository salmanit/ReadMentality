package com.xr.ruidementality.fragment.setting

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ItemDetectorForSetting
import com.xr.ruidementality.adapter.OnRecyclerItemClickListener
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.MyConstant
import com.xr.ruidementality.bean.UserInfo
import com.xr.ruidementality.bean.item.ItemSetting
import com.xr.ruidementality.fragment.FragmentBase
import com.xr.ruidementality.ui.ActivityHome
import com.xr.ruidementality.ui.ActivityLogin
import com.xr.ruidementality.util.FileCacheManager
import com.xr.ruidementality.util.SpHelper
import com.xr.ruidementality.util.UtilNormal
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_base_refresh.*
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber

/**
 * Created by Sage on 2017/10/16.
 * Description:设置页面
 */
class FragmentSetting : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_base_refresh_have_title
    }

    lateinit var footer: View
    var lists = ArrayList<ItemSetting>()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        normalToolbar("设置")
        refresh_base.setEnableOverScrollBounce(false)
        refresh_base.setEnableRefresh(false)
        loading_layout.resetLoadingLayoutByState(0)

        lists.add(ItemSetting("个人信息", "", true))
        lists.add(ItemSetting("定时关闭", "", true))
        lists.add(ItemSetting("2G/3G/4G播放和下载提醒", "", SpHelper.getWifiToast()))
        lists.add(ItemSetting("消息推送", "", SpHelper.getMsgPush()))
        lists.add(ItemSetting("下载和缓存设置", "0k", true))
        lists.add(ItemSetting("关于瑞得心理", "v${UtilNormal.getAppVersionNow()}", true))
        rv_base.apply {
            setBackgroundColor(Color.WHITE)
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(ItemDetectorForSetting())
            var myAdapter = object : AdapterRv<ItemSetting>(lists) {
                override fun layoutId(viewType: Int): Int {
                    return R.layout.item_fragment_setting
                }

                override fun handleData(holder: ViewHolderRv, position: Int, t: ItemSetting) {
                    holder.setText(R.id.tv_title, t.title)
                    holder.setText(R.id.tv_content, t.content)
                    if (position == 2 || position == 3) {
                        holder.getView<ImageView>(R.id.iv_arrow_right).visibility = View.GONE
                        holder.getView<ImageView>(R.id.iv_switch).apply {
                            visibility = View.VISIBLE
                            setImageResource(if (t.open) R.mipmap.rd_switch_open else R.mipmap.rd_switch_close)
                        }
                    } else {
                        holder.getView<ImageView>(R.id.iv_arrow_right).visibility = View.VISIBLE
                        holder.getView<ImageView>(R.id.iv_switch).apply {
                            visibility = View.GONE
                        }
                    }
                }
            }
            var login = !TextUtils.isEmpty(SpHelper.getUserInfo().id)
            footer = LayoutInflater.from(activity).inflate(R.layout.footer_fragment_setting, rv_base, false)
            refreshLogin(login)
            myAdapter.addFooter(footer)
            adapter = myAdapter
            addOnItemTouchListener(object : OnRecyclerItemClickListener(rv_base) {
                override fun onItemClick(vh: RecyclerView.ViewHolder, position: Int) {
                    when (position) {
                        0 -> {
                            if (isLogin()) {
                                (activity as ActivityHome).addFragment(FragmentUserInfo())
                            } else {
                                startActivity(Intent(activity, ActivityLogin::class.java))
                            }

                        }
                        1 -> {

                        }
                        2 -> {
                            var open = lists[position].open
                            SpHelper.saveWifiToast(!open)
                            lists[position].open = !open
                            adapter.notifyItemChanged(position)
                        }
                        3 -> {
                            var open = lists[position].open
                            SpHelper.saveMsgPush(!open)
                            lists[position].open = !open
                            adapter.notifyItemChanged(position)
                        }
                        4 -> {

                        }
                        5 -> {

                        }
                    }
                }
            })
        }

        Observable.create<String> {
            it.onNext(FileCacheManager.getCacheSize(activity))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    lists[4].content = it
                    rv_base.adapter.notifyItemChanged(4)
                }
    }

    override fun eventBusEnable(): Boolean {
        return true
    }

    @Subscriber(tag = "loginStatusChange")
    fun refreshLogin(login: Boolean) {
        footer.findViewById(R.id.tv_login).apply {
            visibility = if (login) View.GONE else View.VISIBLE
            setOnClickListener {
                startActivity(Intent(activity, ActivityLogin::class.java))
            }
        }
        footer.findViewById(R.id.tv_login_out).apply {
            visibility = if (!login) View.GONE else View.VISIBLE
            setOnClickListener {
                //退出登陆
                SpHelper.saveUserInfo(UserInfo())
                EventBus.getDefault().post(false, MyConstant.LOGINSTATUSCHANGE)
                onBackPress()
            }
        }
    }
}