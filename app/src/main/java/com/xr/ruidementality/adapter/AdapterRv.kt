package com.xr.ruidementality.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlin.collections.ArrayList

/**
 * Created by Sage on 2017/9/29.
 * Description:
 */
abstract class AdapterRv<T> : RecyclerView.Adapter<ViewHolderRv> {
    val BASE_ITEM_TYPE_HEAD = 10000
    val BASE_ITEM_TYPE_FOOT = 20000
    var headViews = ArrayList<View>()
    var footerViews = ArrayList<View>()
    var lists: ArrayList<T> = ArrayList()

    constructor() : super()
//    constructor(lists: ArrayList<T>) : super() {
//        this.lists = lists
//    }

    constructor(list: Array<T>) : super() {
        this.lists.clear()
        this.lists.addAll(list)
    }

    constructor(list: List<T>) : super() {
        this.lists.clear()
        this.lists.addAll(list)
    }

    fun addData(list: List<T>) {
        var start = headViews.size + lists.size
        lists.addAll(list)
        notifyItemRangeInserted(start, list.size)
    }

    fun addData(t: T) {
        addData(arrayListOf(t))
    }

    fun setData(list: ArrayList<T>) {
        lists.clear()
        lists.addAll(list)
        notifyDataSetChanged()
    }

    fun setData(t: T) {
        lists.clear()
        lists.add(t)
        notifyDataSetChanged()
    }

    abstract fun layoutId(viewType: Int): Int
    abstract fun handleData(holder: ViewHolderRv, position: Int, t: T)
    override fun onBindViewHolder(holder: ViewHolderRv, position: Int) {
        if (position >= headViews.size && position < headViews.size + getRealDataCount()) {
            val which = position - headViews.size
            var item = getItem(which)
            if (item != null)
                handleData(holder, which, item)
        }
    }

    //正常情况下是不会为空的，只有测试的时候懒得造数据，直接设置count才会为空
    fun getItem(position: Int): T? {
        if (position > getRealDataCount() - 1) {
            return null
        }
        return lists?.get(position)
    }

    override fun getItemCount(): Int {
        return getRealDataCount() + headViews.size + footerViews.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRv {
        headViews
                .filter { (it.tag as Int) == viewType }
                .forEach { return ViewHolderRv.createInstance(it, parent) }
        footerViews
                .filter { (it.tag as Int) == viewType }
                .forEach { return ViewHolderRv.createInstance(it, parent) }
        return ViewHolderRv.createInstance(layoutId(viewType), parent)
    }

    override fun getItemViewType(position: Int): Int {
        if (position < headViews.size) {
            return headViews[position].tag as Int
        }
        var footerPosition = position - (headViews.size + getRealDataCount())
        if (footerPosition >= 0 && footerPosition < footerViews.size) {
            return footerViews[footerPosition].tag as Int
        }
        return 0
    }

    open fun getRealDataCount(): Int {
        return lists.size
    }

    fun addHeader(view: View) {
        view.tag = BASE_ITEM_TYPE_HEAD + headViews.size
        headViews.add(view)
    }

    fun addHeader(position: Int, view: View) {
        view.tag = BASE_ITEM_TYPE_HEAD + headViews.size
        headViews.add(position, view)
    }

    fun setHeader(view: View) {
        headViews.clear()
        view.tag = BASE_ITEM_TYPE_HEAD + headViews.size
        headViews.add(view)
    }

    fun addFooter(view: View) {
        view.tag = BASE_ITEM_TYPE_FOOT + footerViews.size
        footerViews.add(view)
    }

    fun setFooter(view: View) {
        footerViews.clear()
        view.tag = BASE_ITEM_TYPE_FOOT + footerViews.size
        footerViews.add(view)
    }
}