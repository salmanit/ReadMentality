package com.xr.ruidementality.adapter

import com.xr.ruidementality.R

/**
 * Created by Sage on 2017/9/30.
 * Description:简单的展示标签页
 */
class AdapterTags : AdapterRv<String>() {
    override fun layoutId(viewType: Int): Int {
        return R.layout.item_simple_tag
    }

    override fun handleData(holder: ViewHolderRv, position: Int, t: String) {
        holder.setText(R.id.tv_tag_name, t)
    }
}