package com.xr.ruidementality.fragment.search

import android.text.TextUtils
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ItemDetectorV_Bottom
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.bean.MediaSearchResult
import com.xr.ruidementality.bean.SearchVideoS
import com.xr.ruidementality.custom.DefaultCustomCallBack
import com.xr.ruidementality.fragment.FragmentBaseRefresh
import com.xr.ruidementality.util.UtilThis
import kotlinx.android.synthetic.main.fragment_base_refresh.*
import org.simple.eventbus.Subscriber

/**
 * Created by Sage on 2017/10/17.
 * Description:
 */
class FragmentSearchVideo : FragmentBaseRefresh<SearchVideoS>() {

    var searchKey = ""

    override fun eventBusEnable(): Boolean {
        return true
    }

    @Subscriber(tag = "refreshSearchByKeY")
    fun refreshData(key: String) {
        searchKey = key
        page=1
        postHttp()
    }

    override fun changeSomething() {
        adapterBase = object : AdapterRv<SearchVideoS>() {
            override fun layoutId(viewType: Int): Int {
                return R.layout.item_for_result_video
            }

            override fun handleData(holder: ViewHolderRv, position: Int, t: SearchVideoS) {
                holder.setText(R.id.tv_title, t.title)
                holder.setImageUrl(R.id.iv_cover, t.videoThumb, R.mipmap.rd_default_logo_rect)
                holder.setText(R.id.tv_time, UtilThis.getMSBySecond(t.hourLong.toLong()))
                holder.itemView.setOnClickListener {

                }
            }

        }
        rv_base.addItemDecoration(ItemDetectorV_Bottom(2))
    }

    override fun postHttp() {
        APIManager.getSearchResult(2, searchKey, page, pageSize, callbackThis)
    }

    var callbackThis = object : DefaultCustomCallBack<MediaSearchResult>() {
        override fun onSuccess(t: MediaSearchResult) {
            if (page == 1) {
                val haveVideo=t.video_result?.search_result?.isNotEmpty()==true
                if (!haveVideo) {
                   handleEmptyData()
                } else {
                    showContent()
                    adapterBase.setData(t.video_result?.search_result?:ArrayList())
                }

            } else {
                adapterBase.addData(t.video_result?.search_result?:ArrayList())
            }
            refresh_base.isEnableLoadmore = !TextUtils.equals(t.video_result?.page, t.video_result?.total_page)

        }

        override fun onError(type: Int, msg: String) {

            //type:0表示返回失败，1 可能连接服务器失败，2是没有网络
            if (page == 1) {
                when (type) {
                    0 -> {
                        handleEmptyData()
                    }
                    else -> {
                        loading_layout.resetLoadingLayoutByState(2)
                    }
                }
            }

            UtilThis.showHintToast(activity, msg)
        }

        override fun onComplete() {
            if (page == 1) {
                refresh_base.finishRefresh()
            } else {
                refresh_base.finishLoadmore()
            }
            refresh_base.isEnableRefresh = true
        }
    }
}