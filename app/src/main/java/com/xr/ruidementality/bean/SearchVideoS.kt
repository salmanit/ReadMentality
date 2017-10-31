package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/17.
 * Description:搜索出来的视频信息
 */

data class SearchVideoS(
		@SerializedName("title") var title: String, //2017首届巨无聊心理嘉年华
		@SerializedName("hour_long") var hourLong: String, //192
		@SerializedName("video_id") var videoId: String, //55
		@SerializedName("video_thumb") var videoThumb: String //https://static.readymade.cn/Video/5992c51b442bc.jpg
)