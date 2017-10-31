package com.xr.ruidementality.fragment.video

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.AdapterTags
import com.xr.ruidementality.adapter.ItemDetectorH_right
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.app.MyConstant
import com.xr.ruidementality.bean.VideoEntity
import com.xr.ruidementality.fragment.FragmentBaseRefresh
import com.xr.ruidementality.util.UtilThis
import org.simple.eventbus.Subscriber

/**
 * Created by Sage on 2017/9/29.
 * Description:最新或最热视频
 */
class FragmentVideoSort : FragmentBaseRefresh<VideoEntity>() {

    override fun getLayout(): Int {
        return R.layout.fragment_base_refresh
    }

    override fun changeSomething() {
        adapterBase = object : AdapterRv<VideoEntity>() {
            override fun layoutId(viewType: Int): Int {
                return R.layout.item_fragment_video_sort
            }

            override fun handleData(holder: ViewHolderRv, position: Int, t: VideoEntity) {
                holder.setText(R.id.tv_video_title, t.videoTitle)
                holder.setText(R.id.tv_describe, t.videoDescribe)
                holder.setImageUrl(R.id.iv_video_cover, t.coverUrl)
                holder.setText(R.id.tv_video_time, UtilThis.getShowTimeMS(t.hourLong))
                var rv = holder.getView<RecyclerView>(R.id.rv_tags)

                if (rv.adapter == null) {
                    rv.adapter = AdapterTags()
                    rv.addItemDecoration(ItemDetectorH_right(10))
                    rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                }
                (rv.adapter as AdapterTags).setData(t.videoTypeArray)
                holder.getView<ImageView>(R.id.iv_share).setOnClickListener {

                }
            }
        }
    }

    var type = 1//1最新的，2最热的
    fun createType(type: Int): FragmentVideoSort {
        this.type = type
        return this
    }

    override fun eventBusEnable(): Boolean {
        return true
    }

    @Subscriber(tag = "refreshVideo")
    fun refreshByTypeId(cat_id: String) {
        page = 1
        postHttp()
    }

    override fun postHttp() {
        APIManager.getVideoList(MyConstant.currentVideoTypeId, type, page, pageSize,callback)
    }
}