package com.xr.ruidementality.fragment.audio

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ItemDetectorV_Bottom
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.bean.AlbumAudioEntity
import com.xr.ruidementality.bean.AlbumDetail
import com.xr.ruidementality.bean.BaseEntityPage
import com.xr.ruidementality.custom.DefaultCustomCallBack
import com.xr.ruidementality.fragment.FragmentBaseRefresh
import com.xr.ruidementality.fragment.dialog.DiaFragmentBuyCheck
import com.xr.ruidementality.util.UtilThis
import kotlinx.android.synthetic.main.fragment_base_refresh.*

/**
 * Created by Sage on 2017/10/18.
 * Description:
 */
class FragmentAlbumDetail : FragmentBaseRefresh<AlbumAudioEntity>() {

    override fun getLayout(): Int {
        return R.layout.fragment_base_refresh_have_title
    }

    var albumID = 0
    var albumTitle = ""
    lateinit var holderHead: ViewHolderRv
    override fun changeSomething() {
        normalToolbar(albumTitle)
        rv_base.addItemDecoration(ItemDetectorV_Bottom(1))
        adapterBase = object : AdapterRv<AlbumAudioEntity>() {
            override fun layoutId(viewType: Int): Int {
                return R.layout.item_fragment_album_detail
            }

            override fun handleData(holder: ViewHolderRv, position: Int, t: AlbumAudioEntity) {
                holder.setText(R.id.tv_position, "${position + 1}")
                holder.setText(R.id.tv_music_title, t.audioTitle)
                holder.setText(R.id.tv_play_count, "${t.audioPlayNumber}")
                holder.setText(R.id.tv_play_duration, UtilThis.getShowTimeColon(t.audioHourLong))
                holder.setText(R.id.tv_music_fee,"${t.audioPoint}瑞点")
                when (t.buyStatus) {
                    0 -> {//未购买
                        if(TextUtils.equals(t.audioPoint,"0.0")){
                            //免费的
                            holder.setViewVisibleGone(true,R.id.iv_state,R.id.tv_music_free)
                            holder.setViewGone(R.id.tv_music_fee)
                        }else{
                            holder.setViewVisibleGone(true,R.id.tv_music_fee)
                            holder.setViewGone(R.id.tv_music_free,R.id.iv_state)
                        }
                    }
                    else -> {//已购买
                        holder.setViewGone(R.id.tv_music_free, R.id.tv_music_fee)
                        holder.setViewVisibleGone(true,R.id.iv_state)
                    }
                }
            }

        }
        val head = LayoutInflater.from(activity).inflate(R.layout.head_fragment_album_detail, rv_base, false)
        holderHead = ViewHolderRv.Companion.createInstance(head, rv_base)
        adapterBase.addHeader(head)
        adapterBase.addFooter(getPlaceHolderFooter(rv_base))
        pageSize = 9
    }

    override fun postHttp() {
        APIManager.getAlbumAudioList(albumID, page, pageSize, callback)
        if (page == 1) {
            APIManager.getAlbumDetail(albumID, object : DefaultCustomCallBack<AlbumDetail>() {
                override fun onSuccess(t: AlbumDetail) {
                    holderHead.setImageUrl(R.id.iv_cover, t.albumCoverUrl, R.mipmap.rd_default_logo_rect)
                    holderHead.setText(R.id.tv_title, t.albumTitle)
                    holderHead.setViewVisibleGone(R.id.tv_state, t.albumEndStatus == 1)
                    holderHead.setText(R.id.tv_author, "主播：${t.anchorName}")
                    holderHead.setText(R.id.tv_update_time, "更新：${t.updateTime}")
                    holderHead.setText(R.id.tv_catalog, "分类：${t.getCatalog()}")
                    holderHead.getView<TextView>(R.id.tv_buy_album).apply {
                        text = "全辑${t.albumEndMoney}瑞点"
                        visibility = if (t.allAudioBuyStatus == 0) View.VISIBLE else View.INVISIBLE
                    }
                    holderHead.setText(R.id.tv_album_intro, t.albumIntroduction)
                    holderHead.getView<TextView>(R.id.tv_buy_album).setOnClickListener {
                        DiaFragmentBuyCheck().apply {
                            title = "测试内容"
                            moneyNeed = 100f
                            moneyHave = 20f
                            okClickListener = View.OnClickListener {
                                UtilThis.showHintToast(activity, "test..........")
                            }
                        }.show(activity.supportFragmentManager, DiaFragmentBuyCheck::class.java.simpleName)
                    }
                    holderHead.getView<TextView>(R.id.tv_buy_more).setOnClickListener {

                    }
                }

                override fun onError(type: Int, msg: String) {

                }
            })
        }
    }

    override fun onSuccessOther(t: BaseEntityPage<AlbumAudioEntity>) {
        holderHead.setText(R.id.tv_song_count, "已更新${adapterBase.getRealDataCount()}首")
    }
}