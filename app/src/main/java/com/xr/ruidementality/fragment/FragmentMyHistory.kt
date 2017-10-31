package com.xr.ruidementality.fragment

import android.support.v7.widget.RecyclerView
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ItemDetectorV_Bottom
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.bean.HistoryListen
import com.xr.ruidementality.util.UtilThis
import kotlinx.android.synthetic.main.fragment_base_refresh.*

/**
 * Created by Sage on 2017/10/25.
 * Description:收听历史
 */
class FragmentMyHistory : FragmentBaseRefresh<HistoryListen>() {

    override fun getLayout(): Int {
        return R.layout.fragment_base_refresh_have_title
    }
    override fun changeSomething() {
        normalToolbar("历史记录")
        adapterBase = object : AdapterRv<HistoryListen>() {
            override fun layoutId(viewType: Int): Int {
                return R.layout.item_fragment_my_history
            }

            override fun handleData(holder: ViewHolderRv, position: Int, t: HistoryListen) {
                holder.setText(R.id.tv_album_title,t.album?.albumTitle?:"")
                holder.setImageUrl(R.id.iv_his_pic,t.album?.albumCoverUrl?:"")
                holder.setText(R.id.tv_audio_title,t.audioName)
                holder.setText(R.id.tv_last_time,"上次播放至${UtilThis.getShowTimeMS_CN(t.audioLastPlayTime.toLong())}")
            }
        }
        rv_base.addItemDecoration(ItemDetectorV_Bottom(1))
        adapterBase.addFooter(getPlaceHolderFooter(rv_base))
    }

    override fun onItemClickNoHead(vh: RecyclerView.ViewHolder, position: Int, t: HistoryListen?) {

    }
    override fun postHttp() {
        APIManager.getMyHistoryListen(page, pageSize, callback)
    }
}