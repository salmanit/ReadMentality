package com.xr.ruidementality.fragment.audio

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.AdapterTags
import com.xr.ruidementality.adapter.ItemDetectorH_right
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.app.MyConstant
import com.xr.ruidementality.bean.AlbumAudioEntity
import com.xr.ruidementality.bean.AlbumEntity
import com.xr.ruidementality.fragment.FragmentBaseRefresh
import org.simple.eventbus.Subscriber

/**
 * Created by Sage on 2017/9/29.
 * Description:最新或最热音频
 */
class FragmentAudioSort : FragmentBaseRefresh<AlbumEntity>() {

    override fun eventBusEnable(): Boolean {
        return true
    }

    override fun changeSomething() {
        adapterBase = object : AdapterRv<AlbumEntity>() {
            override fun layoutId(viewType: Int): Int {
                return R.layout.item_audio_sort
            }

            override fun handleData(holder: ViewHolderRv, position: Int, t: AlbumEntity) {
                holder.setText(R.id.tv_fm_name, t.albumTitle)
                holder.setText(R.id.tv_album_name, t.albumSummarize)
                if (t.albumEndStatus == 1) {
                    holder.getView<TextView>(R.id.is_finish_tv).visibility = View.VISIBLE
                    holder.setText(R.id.tv_ruidian, getString(R.string.rd_format_ruidian, t.albumEndMoney))
                } else {
                    holder.getView<TextView>(R.id.is_finish_tv).visibility = View.GONE
                    if (TextUtils.equals(t.albumAudioMoney, "免费试听")) {
                        holder.setText(R.id.tv_ruidian, t.albumAudioMoney)
                    } else {
                        holder.setText(R.id.tv_ruidian, getString(R.string.rd_format_ruidian_single, t.albumAudioMoney))
                    }
                }
                var rv = holder.getView<RecyclerView>(R.id.rv_tags)
                if (rv.adapter == null) {
                    rv.adapter = AdapterTags()
                    rv.addItemDecoration(ItemDetectorH_right(10))
                    rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                }
                (rv.adapter as AdapterTags).setData(t.categoryArray)

                //图片
                holder.setImageUrl(R.id.iv_fm_gd, t.albumCoverUrl)
            }
        }
    }

    var type = 1//1最新的，2最热的
    fun createType(type: Int): FragmentAudioSort {
        this.type = type
        return this
    }

    @Subscriber(tag = "refreshAudio")
    fun refreshByCatalogID(cat_id: String) {
        page = 1
        postHttp()
    }

    override fun postHttp() {
        APIManager.getAlbumList(MyConstant.currentAudioTypeId, type, page, pageSize, callback)
    }

    override fun onItemClickNoHead(vh: RecyclerView.ViewHolder, position: Int, t: AlbumEntity?) {
        addFragment(FragmentAlbumDetail().apply {
            albumID = t!!.id
            albumTitle = t.albumTitle
        })
    }
}