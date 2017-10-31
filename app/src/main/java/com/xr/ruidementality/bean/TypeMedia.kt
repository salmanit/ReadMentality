package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/12.
 * Description:音频，视频，图文类型
 * small_thumb 是分类灰色的图
 * small_hover_thumb 是分类红色的图，选中状态
 * big_thumb 这个是红色的带圈的图，主要是给首页那几个分类用的
 */


data class TypeMedia(
		@SerializedName("catid") var catid: String, //25
		@SerializedName("catname") var catname: String, //随便听听
		@SerializedName("small_thumb") var smallThumb: String, //https://static.readymade.cn/Category_thumb/5992cb85a0999.png
		@SerializedName("small_hover_thumb") var smallHoverThumb: String, //https://static.readymade.cn/Category_thumb/5992cb8c9d6db.png
		@SerializedName("big_thumb") var bigThumb: String //https://static.readymade.cn/Category_thumb/598c0d4163092.png
)