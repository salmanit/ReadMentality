package com.xr.ruidementality.adapter

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.SparseArray
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xr.ruidementality.R
import com.bumptech.glide.request.RequestOptions


/**
 * Created by Sage on 2017/9/29.
 * Description:
 */
open class ViewHolderRv(itemView: View, var parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {

    private val mViews: SparseArray<View> = SparseArray()

    companion object {
        fun createInstance(layoutId: Int, parent: ViewGroup): ViewHolderRv {
            return ViewHolderRv(LayoutInflater.from(parent.context).inflate(layoutId, parent, false), parent)
        }

        fun createInstance(itemView: View, parent: ViewGroup): ViewHolderRv {
            return ViewHolderRv(itemView, parent)
        }
    }

    init {
        if(itemView.background==null){
            val typedValue = TypedValue();
            if (itemView.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
                itemView.setBackgroundResource(typedValue.resourceId);
            }
        }
    }
    fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    fun setText(viewId: Int, @StringRes txtRes: Int) {
        setText(viewId, itemView.context.getText(txtRes))
    }

    fun setText(viewId: Int, msg: CharSequence?) {
        val textView = getView<TextView>(viewId)
        textView.text = msg
        textView.visibility = View.VISIBLE
    }

    fun setImageResource(viewId: Int, resId: Int) {
        val imageView = getView<ImageView>(viewId)
        imageView.setImageResource(resId)
    }

    fun setViewVisibleGone(viewId: Int, visible: Boolean) {
        getView<View>(viewId).visibility = if (visible) View.VISIBLE else View.GONE
    }
    fun setViewVisibleGone( visible: Boolean,vararg viewIds: Int) {
        viewIds.forEach { getView<View>(it).visibility = if (visible) View.VISIBLE else View.GONE }
    }

    fun setViewGone(vararg viewIds: Int) {
        viewIds.forEach { getView<View>(it).visibility = View.GONE }

    }

    fun setImageUrl(viewId: Int, url: String) {
        setImageUrl(viewId,url,0)
    }

    fun setImageUrl(viewId: Int, url: String, errorRes: Int) {
        var iv = getView<ImageView>(viewId)
        if (TextUtils.isEmpty(url)) {
            Glide.with(iv.context).load(errorRes).into(iv)
            return
        }
        val options = RequestOptions()
                .centerCrop()
                .placeholder(errorRes)
                .error(errorRes)

        Glide.with(iv.context).load(url)
        if (url.startsWith("drawable://")) {
            //本地图片
            val resID = url.substring("drawable://".length).toInt()
            Glide.with(iv.context).load(resID).apply(options).into(iv)
        } else {
            Glide.with(iv.context).load(url).apply(options).into(iv)
        }
    }
}