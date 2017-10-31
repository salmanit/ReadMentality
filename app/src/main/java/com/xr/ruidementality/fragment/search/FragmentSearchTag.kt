package com.xr.ruidementality.fragment.search

import android.graphics.Color
import android.os.Bundle
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.bean.SearchHotTag
import com.xr.ruidementality.custom.CustomCallBack
import com.xr.ruidementality.fragment.FragmentBase
import com.xr.ruidementality.widget.TextViewWithBg
import kotlinx.android.synthetic.main.fragment_search_tag.*
import org.simple.eventbus.EventBus

/**
 * Created by Sage on 2017/10/17.
 * Description:热门字段展示页面
 */
class FragmentSearchTag : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_search_tag
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_tags.apply {
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
            }

            adapter = object : AdapterRv<SearchHotTag>() {
                override fun layoutId(viewType: Int): Int {
                    return R.layout.item_search_hot_tag
                }

                override fun handleData(holder: ViewHolderRv, position: Int, t: SearchHotTag) {
                    holder.setText(R.id.tv_tag_name, t.keyword)
                    holder.getView<TextViewWithBg>(R.id.tv_tag_name).apply {
                        text=t.keyword
                        var color=if(position<3) resources.getColor(R.color.default_color_theme) else Color.parseColor("#888888")
                        setTextColor(color)
                        colorStroke=color
                        postInvalidate()
                        setOnClickListener {
                            EventBus.getDefault().post(t.keyword,"searchKeyWord")
                        }
                    }
                }
            }
        }

        APIManager.getHotWords(object : CustomCallBack<ArrayList<SearchHotTag>> {
            override fun onSuccess(t: ArrayList<SearchHotTag>) {
                (rv_tags.adapter as AdapterRv<SearchHotTag>).setData(t)
            }

            override fun onError(type: Int, msg: String) {

            }

            override fun onComplete() {

            }
        })
    }

}