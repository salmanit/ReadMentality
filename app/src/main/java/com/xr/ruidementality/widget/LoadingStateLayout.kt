package com.xr.ruidementality.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.xr.ruidementality.R

/**
 * Created by Sage on 2017/10/13.
 * Description:数据加载的各种状态封装处理
 */
class LoadingStateLayout : LinearLayout {

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    fun initView() {
        var view = LayoutInflater.from(context).inflate(R.layout.layout_loading_state, this, false)
        addView(view)
        state_gif = view.findViewById(R.id.state_gif) as GifView
        iv_state = view.findViewById(R.id.iv_state) as ImageView
        tv_state = view.findViewById(R.id.tv_state) as TextView
        tv_reload = view.findViewById(R.id.tv_reload) as TextView
        tv_reload.setOnClickListener {
            resetLoadingLayoutByState(1)
            if (clickListener != null) {
                clickListener?.loadAgainClick(this)
            }
        }
        resetLoadingLayoutByState(1)
    }

    lateinit var state_gif: GifView
    lateinit var iv_state: ImageView
    lateinit var tv_state: TextView
    lateinit var tv_reload: TextView
    //数据为空的时候提示文字和图片
    var resEmptyPic = R.mipmap.rd_not_subscription_pic
    var resEmptyWord = R.string.rd_not_subscription
    //网络异常的时候提示文字和图片
    var resNotNetPic = R.mipmap.rd_not_net_pic
    var resNotNetWord = R.string.rd_not_net_and_refresh
    //加载中的时候提示文字和图片
    var resLoadingPic = R.raw.rd_default_loading
    var resLoadingWord = R.string.rd_state_loading

    fun resetLoadingLayoutByState(state: Int) {
        try {
            showHidden(state)
        } catch(e: Exception) {
        }
        when (state) {
            1 -> {//加载中
                state_gif.visibility = View.VISIBLE
                state_gif.setMovieResource(resLoadingPic)
                iv_state.visibility = View.GONE
                tv_state.setText(resLoadingWord)
                tv_reload.visibility = View.GONE
            }
            2 -> {//网络错误
                try {
                    iv_state.visibility = View.VISIBLE
                    iv_state.setImageResource(resNotNetPic)
                    tv_state.setText(resNotNetWord)
                    tv_reload.visibility = View.VISIBLE
                    state_gif.visibility = View.GONE
                } catch(e: Exception) {
                }
            }
            3 -> {//没有数据
                try {
                    iv_state.visibility = View.VISIBLE
                    iv_state.setImageResource(resEmptyPic)
                    tv_state.setText(resEmptyWord)
                    state_gif.visibility = View.GONE
                    tv_reload.visibility = View.GONE
                } catch(e: Exception) {
                }
            }
            else -> {

            }

        }

    }

    fun showHidden(state: Int) {
        if (state in 1..3) {
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    }

    interface LoadAgainClickListener {
        abstract fun loadAgainClick(view: View)
    }

    var clickListener: LoadAgainClickListener? = null

}