package com.xr.ruidementality.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.OnRecyclerItemClickListener
import com.xr.ruidementality.bean.BaseEntityPage
import com.xr.ruidementality.custom.DefaultCustomCallBack
import com.xr.ruidementality.util.UtilThis
import com.xr.ruidementality.widget.LoadingStateLayout
import kotlinx.android.synthetic.main.fragment_base_refresh.*

/**
 * Created by Sage on 2017/9/29.
 * Description:列表类的基类
 */
abstract class FragmentBaseRefresh<T> : FragmentBase() {

    override fun getLayout(): Int {
        return R.layout.fragment_base_refresh
    }

    var page = 1
    open var pageSize = 10
    lateinit var adapterBase: AdapterRv<T>
    lateinit var layoutManageBase: RecyclerView.LayoutManager
    private fun initSomeThing() {

        refresh_base.apply {
            setOnRefreshListener {
                page = 1
                postHttp()
            }

            setOnLoadmoreListener {
                page++
                postHttp()
            }
        }
        layoutManageBase = LinearLayoutManager(activity)

    }

    abstract fun changeSomething()
    abstract fun postHttp()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSomeThing()
        rv_base.layoutManager = layoutManageBase
        changeSomething()
        loading_layout.clickListener = object : LoadingStateLayout.LoadAgainClickListener {
            override fun loadAgainClick(view: View) {
                postHttp()
            }
        }
        rv_base.apply {
            adapter = adapterBase
        }
        refresh_base.isEnableLoadmore = false
        refresh_base.isEnableRefresh = false
        rv_base.addOnItemTouchListener(object : OnRecyclerItemClickListener(rv_base) {
            override fun onItemClick(vh: RecyclerView.ViewHolder, position: Int) {
                var heads = adapterBase.headViews.size
                if (position >= heads && position < adapterBase.getRealDataCount() + heads) {
                    val realP = position - heads
                    onItemClickNoHead(vh, realP, adapterBase.getItem(realP))
                }

            }
        })
        postHttp()
    }

    fun showLoading() {
        loading_layout.visibility = View.VISIBLE
        rv_base.visibility = View.GONE
    }

    fun showContent() {
        loading_layout.visibility = View.GONE
        rv_base.visibility = View.VISIBLE
    }

    open fun onItemClickNoHead(vh: RecyclerView.ViewHolder, position: Int, t: T?) {

    }

    open fun withHead(): Boolean {
        return adapterBase.headViews.size > 0
    }

    var callback = object : DefaultCustomCallBack<BaseEntityPage<T>>() {
        override fun onSuccess(t: BaseEntityPage<T>) {
            if (page == 1) {
                if (t.list.size == 0) {
                    handleEmptyData()
                } else {
                    showContent()
                    adapterBase.setData(t.list)
                }

            } else {
                adapterBase.addData(t.list)
            }
            refresh_base.isEnableLoadmore = !TextUtils.equals(t.page, t.total_page)
            onSuccessOther(t)
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
    //可能需要一些额外操作
    open fun onSuccessOther(t: BaseEntityPage<T>){

    }
    open fun handleEmptyData() {
        if (withHead()) {
            showContent()
        } else {
            showLoading()
            loading_layout.resetLoadingLayoutByState(3)
        }
        adapterBase.setData(ArrayList())
    }
}