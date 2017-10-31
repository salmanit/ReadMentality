package com.xr.ruidementality.fragment.search

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ItemDetectorV_top
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.bean.MediaSearchResult
import com.xr.ruidementality.bean.SearchAlbumS
import com.xr.ruidementality.bean.SearchAudioS
import com.xr.ruidementality.custom.DefaultCustomCallBack
import com.xr.ruidementality.fragment.FragmentBaseRefresh
import com.xr.ruidementality.util.UtilThis
import kotlinx.android.synthetic.main.fragment_base_refresh.*
import org.simple.eventbus.Subscriber

/**
 * Created by Sage on 2017/10/17.
 * Description:音频搜索的结果
 */
class FragmentSearchAudio : FragmentBaseRefresh<String>() {


    var searchKey = ""
    override var pageSize: Int
        get() = 10000
        set(value) {}

    var titles = ArrayList<String>()

    override fun eventBusEnable(): Boolean {
        return true
    }

    @Subscriber(tag = "refreshSearchByKeY")
    fun refreshData(key: String) {
        searchKey = key
        page = 1
        postHttp()
    }

    override fun changeSomething() {
        adapterBase = object : AdapterRv<String>() {
            override fun layoutId(viewType: Int): Int {
                return R.layout.item_fragment_search_audio
            }

            override fun handleData(holder: ViewHolderRv, position: Int, t: String) {
                holder.setText(R.id.tv_title, t)
                var rv = holder.getView<RecyclerView>(R.id.rv_result)
                if (rv.adapter == null) {
                    rv.apply {
                        layoutManager = LinearLayoutManager(activity)
                        addItemDecoration(ItemDetectorV_top(2))
                    }
                }
                if (TextUtils.equals("专辑", t)) {
                    rv.adapter = adapterAlbum
                    adapterAlbum.setData(mediaSearchResult?.album_result?.search_result ?: ArrayList())
                    holder.setViewVisibleGone(R.id.space, true)
                } else {
                    rv.adapter = adapterAudio
                    adapterAudio.setData(mediaSearchResult?.audio_result?.search_result ?: ArrayList())
                    holder.setViewGone(R.id.space)
                }
            }

        }
        adapterBase.addFooter(getPlaceHolderFooter(rv_base))
    }

    override fun postHttp() {
        APIManager.getSearchResult(1, searchKey, page, pageSize, callbackThis)
    }

    var mediaSearchResult: MediaSearchResult? = null
    var callbackThis = object : DefaultCustomCallBack<MediaSearchResult>() {
        override fun onSuccess(t: MediaSearchResult) {
            mediaSearchResult = t
            val haveAlbum=t.album_result?.search_result?.isNotEmpty() == true
            val haveAudio=t.audio_result?.search_result?.isNotEmpty() == true
            if (!haveAlbum && !haveAudio) {
                handleEmptyData()
            } else {
                titles.clear()
                showContent()
                if (haveAlbum) {
                    titles.add("专辑")
                }
                if (haveAudio) {
                    titles.add("声音")
                }
                adapterBase.setData(titles)
            }

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


    var adapterAlbum = object : AdapterRv<SearchAlbumS>() {
        override fun layoutId(viewType: Int): Int {
            return R.layout.item_for_result_album
        }

        override fun handleData(holder: ViewHolderRv, position: Int, t: SearchAlbumS) {
            holder.setText(R.id.tv_title, t.title)
            holder.setImageUrl(R.id.iv_cover, t.albumThumb, R.mipmap.rd_default_logo_rect)
            holder.itemView.setOnClickListener {

            }
        }

    }

    var adapterAudio = object : AdapterRv<SearchAudioS>() {
        override fun layoutId(viewType: Int): Int {
            return R.layout.item_for_result_audio
        }

        override fun handleData(holder: ViewHolderRv, position: Int, t: SearchAudioS) {
            holder.setText(R.id.tv_title, t.title)
            holder.setImageUrl(R.id.iv_cover, t.audioThumb, R.mipmap.rd_default_logo_rect)
            holder.setText(R.id.tv_time, UtilThis.getMSBySecond(t.hourLong.toLong()))
            holder.itemView.setOnClickListener {

            }
        }
    }


}