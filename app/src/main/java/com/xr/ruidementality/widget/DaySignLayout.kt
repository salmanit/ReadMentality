package com.xr.ruidementality.widget

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.bean.DaySignPic
import com.xr.ruidementality.bean.HomePageBase
import com.xr.ruidementality.custom.CustomCallBack
import com.xr.ruidementality.util.UtilThis
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Sage on 2017/10/12.
 * Description:封装一下日前的布局，方便点击全屏的处理
 * 需要配合DaySignManager一起监听activity的后退操作
 */
class DaySignLayout : FrameLayout {
    constructor(context: Context) : super(context) {
        initSomething()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initSomething()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initSomething()
    }

    val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
    lateinit var mContainer: FrameLayout
    lateinit var dayView: View

    var fullScreen = false//是否是全屏状态
    fun initSomething() {
        mContainer = FrameLayout(context)
        mContainer.setBackgroundColor(Color.WHITE)
        this.addView(mContainer, params)

        dayView = LayoutInflater.from(context).inflate(R.layout.layout_day_sign, null)
        mContainer.addView(dayView, params)
    }

    lateinit var holder: ViewHolderRv
    lateinit var t: HomePageBase
    fun setData(t: HomePageBase) {
        this.t = t
        holder = ViewHolderRv.createInstance(dayView, this)
        holder.setImageUrl(R.id.iv_day_pic, t.adpic.adfile)
        holder.setText(R.id.tv_time, SimpleDateFormat("yyyy/MM/dd", Locale.CHINESE).format(Date(t.showTime * 1000)))
        holder.getView<ImageView>(R.id.iv_like).apply {
            setImageResource(if (t.adpic.upvoteStatus == 1) R.mipmap.rd_like_checked else R.mipmap.rd_me_item_collection)
            setOnClickListener {
                upLike(t.adpic)
            }
        }

        holder.getView<ImageView>(R.id.iv_share).setOnClickListener {

        }
        holder.getView<View>(R.id.iv_close).setOnClickListener {
            exitFullScreen()
        }
        dayView.setOnClickListener {
            if (fullScreen) {
                exitFullScreen()
            } else {
                enterFullScreen()
            }

        }
    }

    fun resetPicColor() {
        var res = if (t.adpic.upvoteStatus == 1) R.mipmap.rd_like_checked
        else {
            if (fullScreen) R.mipmap.rd_white_love else R.mipmap.rd_me_item_collection
        }
        holder.getView<ImageView>(R.id.iv_like).setImageResource(res)
        holder.getView<View>(R.id.iv_close).visibility = if (fullScreen) View.VISIBLE else View.GONE
        holder.getView<ImageView>(R.id.iv_share).setImageResource(
                if (!fullScreen) R.mipmap.rd_gray_share
                else R.mipmap.rd_white_share
        )
    }

    fun enterFullScreen() {
        this.removeView(mContainer)
        mContainer.setBackgroundColor(Color.BLACK)
        val contentView = (context as Activity).findViewById(android.R.id.content) as ViewGroup
        contentView.addView(mContainer, params)
        fullScreen = true
        resetPicColor()
        DaySignManager.daySignLayout=this
    }

    fun exitFullScreen() {
        mContainer.setBackgroundColor(Color.WHITE)
        val contentView = (context as Activity).findViewById(android.R.id.content) as ViewGroup
        contentView.removeView(mContainer)
        this.addView(mContainer, params)
        fullScreen = false
        resetPicColor()
        DaySignManager.daySignLayout=null
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

    }


    var isLikeUpdate = false
    fun upLike(daySignPic: DaySignPic) {
        if (isLikeUpdate) {
            return
        } else {
            isLikeUpdate = true
        }
        var newStatus = (daySignPic.upvoteStatus + 1) % 2
        APIManager.upLike(2, daySignPic.id, newStatus, object : CustomCallBack<String> {
            override fun onSuccess(t: String) {
                UtilThis.showHintToast(context, t)
                daySignPic.upvoteStatus = newStatus
                resetPicColor()
            }

            override fun onError(type: Int, msg: String) {

            }

            override fun onComplete() {
                isLikeUpdate = false
            }
        })
    }

}