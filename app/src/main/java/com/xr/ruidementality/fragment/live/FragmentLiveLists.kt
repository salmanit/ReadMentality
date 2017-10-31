package com.xr.ruidementality.fragment.live

import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.bean.LiveEntity
import com.xr.ruidementality.fragment.FragmentBaseRefresh
import kotlinx.android.synthetic.main.layout_toolbar_normal.*
import org.simple.eventbus.Subscriber

/**
 * Created by Sage on 2017/8/8.
 * Description:直播列表页面
 */
class FragmentLiveLists : FragmentBaseRefresh<LiveEntity>() {

    override fun getLayout(): Int {
        return R.layout.fragment_base_refresh_have_title
    }

    override fun changeSomething() {
        tv_title.text = "瑞得直播"
        iv_back.setOnClickListener {
            activity.supportFragmentManager.popBackStack()
        }
        adapterBase = object : AdapterRv<LiveEntity>() {
            override fun layoutId(viewType: Int): Int {
                return R.layout.item_live_lists
            }

            override fun handleData(holder: ViewHolderRv, position: Int, data: LiveEntity) {
                holder.setImageUrl(R.id.iv_live_cover, data.tv_cover_url)
                holder.setText(R.id.tv_live_title, data.tv_title)
                holder.setText(R.id.tv_ruidian, data.tv_point)
                holder.setText(R.id.tv_live_host, "主播: ${data.anchor_name}")

                var showTime = data.tv_start_time
                if (data.tv_status == 4) {
                    showTime = getShowTime(data.tv_video_hour_long)
                    holder.setViewGone(R.id.tv_live_state)
                } else {
                    holder.setText(R.id.tv_live_state, data.tv_status_name)
                }
                holder.setText(R.id.tv_live_time, showTime)
            }

        }
    }

    fun getShowTime(time: Long): String {
        var minute = time / 60
        var seconds = time % 60
        return "${if (minute < 10) "0" else ""}${minute}'${if (seconds < 10) "0" else ""}${seconds}''"
    }


    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun eventBusEnable(): Boolean {
        return true
    }

    @Subscriber(tag = "updateLiveEntity")
    fun updateItem(liveEntity: LiveEntity) {
        try {
            if (!isAdded) {
                return
            }
//            for (i in 0..defaultLists.size) {
//                var old = defaultLists[i]
//                if (old.tv_id == liveEntity.tv_id) {
//                    if (old.tv_status != liveEntity.tv_status) {
//                        defaultLists[i].tv_status = liveEntity.tv_status
//                        defaultLists[i].tv_status_name = liveEntity.tv_status_name
//                        defaultAdapter.lists = defaultLists
//                    }
//                    break
//                }
//            }
        } catch(e: Exception) {
        }
    }

    override fun postHttp() {
        APIManager.getLiveList(page, pageSize, callback)
    }
}