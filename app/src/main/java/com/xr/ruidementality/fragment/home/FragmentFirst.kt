package com.xr.ruidementality.fragment.home

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.*
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.app.MyConstant
import com.xr.ruidementality.bean.*
import com.xr.ruidementality.custom.CustomCallBack
import com.xr.ruidementality.custom.DefaultCustomCallBack
import com.xr.ruidementality.fragment.FragmentBaseRefresh
import com.xr.ruidementality.fragment.live.FragmentLiveLists
import com.xr.ruidementality.fragment.search.FragmentSearch
import com.xr.ruidementality.ui.ActivityHome
import com.xr.ruidementality.util.UtilNormal
import com.xr.ruidementality.util.UtilThis
import com.xr.ruidementality.widget.DaySignLayout
import kotlinx.android.synthetic.main.fragment_base_refresh.*
import kotlinx.android.synthetic.main.fragment_first.*
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Sage on 2017/9/29.
 * Description:首页
 */
class FragmentFirst : FragmentBaseRefresh<HomePageBase>() {
    override fun getLayout(): Int {
        return R.layout.fragment_first
    }

    override fun changeSomething() {
        addPaddingMatchStateBar(layout_toolbar)
        tv_left.setOnClickListener {
            (activity as ActivityHome).addFragment(FragmentLiveLists(), 1)
        }
        iv_right.setOnClickListener {
            addFragment(FragmentSearch())
        }

        addView = LayoutInflater.from(activity).inflate(R.layout.head_home_catalog_live, null)
        adapterBase = object : AdapterRv<HomePageBase>() {
            override fun layoutId(viewType: Int): Int {
                return R.layout.item_fragment_first
            }

            override fun handleData(holder: ViewHolderRv, position: Int, t: HomePageBase) {
                //下面部分为日签的
                if (t.adpic == null) {
                    holder.getView<DaySignLayout>(R.id.layout_day_sign).visibility = View.GONE
                } else {
                    holder.getView<DaySignLayout>(R.id.layout_day_sign).visibility = View.VISIBLE
                    holder.getView<DaySignLayout>(R.id.layout_day_sign).setData(t)
                }


                //当天的这里要增加音频目录以及直播图片
                holder.getView<LinearLayout>(R.id.layout_add).removeAllViews()
                if (position == 0) {
                    holder.getView<LinearLayout>(R.id.layout_add).addView(addView)
                }

                //下边的为每日推荐的音频视频数据
                resetDayData(holder.getView(R.id.rv_first_item), t.list)
                var date = "--  End ${SimpleDateFormat("yyyy.MM.dd", Locale.CHINESE).format(Date(t.showTime * 1000))}  --"
                holder.setText(R.id.tv_date, date)
            }
        }
        var footer = LayoutInflater.from(activity).inflate(R.layout.footer_fragment_first, rv_base, false)
        tv_footer_show = footer.findViewById(R.id.tv_more) as TextView
        footer.setOnClickListener {
            if (refresh_base.isEnableLoadmore) {
                refresh_base.autoLoadmore()
                page++
                postHttp()
            }
        }
        adapterBase.addFooter(footer)
        rv_base.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                var manager = recyclerView.layoutManager as LinearLayoutManager
                (activity as ActivityHome).showFirstTopOrHidden(manager.findLastVisibleItemPosition() > 0)
            }
        })

    }

    override fun eventBusEnable(): Boolean {
        return true
    }

    @Subscriber(tag = "FragmentFirstGoToTop")
    fun goToTop(temp: String) {
        rv_base.smoothScrollToPosition(0)
    }

    lateinit var tv_footer_show: TextView


    /**处理获取到的单日的数据*/
    fun resetDayData(rv: RecyclerView, list: List<HomePageData>) {
        if (rv.adapter == null) {
            rv.apply {
                layoutManager = LinearLayoutManager(activity)
                addItemDecoration(ItemDetectorV_top(UtilNormal.dp2px(activity, 10F).toInt()))
                adapter = object : AdapterRv<HomePageData>(list) {
                    override fun layoutId(viewType: Int): Int {
                        when (viewType) {
                            1 -> return R.layout.item_fragment_first_item
                            2 -> return R.layout.item_fragment_video_sort
                            else -> return R.layout.item_fragment_first_item
                        }
                    }

                    override fun handleData(holder: ViewHolderRv, position: Int, t: HomePageData) {
                        when (t.positionType) {
                            1 -> {//音频
                                //专辑数据
                                holder.setImageUrl(R.id.iv_head, t.albumThumb)
                                holder.setText(R.id.tv_album_title, t.albumTitle)
                                holder.setText(R.id.tv_anchor_name, t.anchorName)
                                holder.getView<ViewGroup>(R.id.layout_album).setOnClickListener {

                                }
                                //专辑里推荐的音频数据
                                holder.setText(R.id.tv_video_title, t.audioTitle)
                                holder.setText(R.id.tv_describe, t.audioIntroduction)
                                holder.setImageUrl(R.id.iv_video_cover, t.audioThumb, R.mipmap.rd_default_logo_rect)
                                holder.setText(R.id.tv_video_time, UtilThis.getShowTimeMS(t.audioHourLong))
                                var rv = holder.getView<RecyclerView>(R.id.rv_tags)

                                if (rv.adapter == null) {
                                    rv.adapter = AdapterTags()
                                    rv.addItemDecoration(ItemDetectorH_right(10))
                                    rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                                }
                                (rv.adapter as AdapterTags).setData(t.albumTypeName)
                                holder.getView<ImageView>(R.id.iv_share).setOnClickListener {

                                }
                            }
                            2 -> {//视频
                                holder.setText(R.id.tv_video_title, t.videoTitle)
                                holder.setText(R.id.tv_describe, t.videoDescribe)
                                holder.setImageUrl(R.id.iv_video_cover, t.coverThumb, R.mipmap.rd_default_logo_rect)
                                holder.setText(R.id.tv_video_time, UtilThis.getShowTimeMS(t.hourLong))
                                var rv = holder.getView<RecyclerView>(R.id.rv_tags)

                                if (rv.adapter == null) {
                                    rv.adapter = AdapterTags()
                                    rv.addItemDecoration(ItemDetectorH_right(10))
                                    rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                                }
                                (rv.adapter as AdapterTags).setData(t.videoTypeName)
                                holder.getView<ImageView>(R.id.iv_share).setOnClickListener {

                                }
                            }
                            3 -> {//图文

                            }
                        }
                    }

                    override fun getItemViewType(position: Int): Int {
                        var type = super.getItemViewType(position)
                        if (type == 0) {
                            var realPosition = position - headViews.size
                            var data = getItem(realPosition)
                            return data?.positionType ?: 0
                        } else {
                            return type
                        }
                    }
                }
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    var time = 0L
    override fun postHttp() {
        if (page == 1) {
            time = 0L
        }
        APIManager.getHomePageData(time, object : CustomCallBack<HomePageBase> {
            override fun onSuccess(t: HomePageBase) {
                time = t.showTime
                refresh_base.isEnableLoadmore = t.showMore == 1
                tv_footer_show.text = if (t.showMore == 1) "查看前一天" else "查看更多，请进音频专辑~"
                if (page == 1) {
                    showContent()
                    adapterBase.setData(t)
                    resetAddViewData()
                } else {
                    if (t.list.isNotEmpty())
                        adapterBase.addData(t)
                }
                refresh_base.isEnableLoadmore = t.showMore == 1
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
        })
    }


    lateinit var addView: View
    fun resetAddViewData() {
        APIManager.getCategoryList(1, 3, object : DefaultCustomCallBack<ArrayList<TypeMedia>>() {
            override fun onSuccess(t: ArrayList<TypeMedia>) {
                t.add(TypeMedia("0", "全部", "", "", ""))
                var rv = addView.findViewById(R.id.rv_home_catalog) as RecyclerView
                if (rv.adapter == null) {
                    rv.apply {
                        layoutManager = GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false)
                        adapter = object : AdapterRv<TypeMedia>() {
                            override fun layoutId(viewType: Int): Int {
                                return R.layout.item_home_catalog
                            }

                            override fun handleData(holder: ViewHolderRv, position: Int, t: TypeMedia) {
                                if (TextUtils.equals(t.catid, "0")) {
                                    holder.setImageResource(R.id.iv_catalog, R.mipmap.rd_home_page_all_categories)
                                } else {
                                    holder.setImageUrl(R.id.iv_catalog, t.bigThumb)
                                }
                                holder.setText(R.id.tv_catalog_name, t.catname)
                                holder.itemView.setOnClickListener {
                                    if (TextUtils.equals(t.catid, "0")) {
                                        //跳到
                                        (activity as ActivityHome).addFragment(FragmentTypeChoose(1), 1)
                                    } else {
                                        if (!TextUtils.equals(t.catid, MyConstant.currentAudioTypeId)) {
                                            MyConstant.currentAudioTypeId = t.catid
                                            EventBus.getDefault().post(t.catid, "refreshAudio")
                                        }
                                        (activity as ActivityHome).goWhere(1)
                                    }
                                }
                            }

                        }

                    }
                }
                (rv.adapter as AdapterRv<TypeMedia>).setData(t)
            }

            override fun onError(type: Int, msg: String) {

            }
        })
        APIManager.getHomeLiveInfo(object : DefaultCustomCallBack<HomeLiveInfo>() {
            override fun onSuccess(t: HomeLiveInfo) {
                var iv = addView.findViewById(R.id.iv_live) as ImageView
                UtilThis.loadPic(iv, t.liveThumb)
            }

            override fun onError(type: Int, msg: String) {

            }
        })

    }
}