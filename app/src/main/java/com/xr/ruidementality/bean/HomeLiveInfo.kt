package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/12.
 * Description: 首页获取的直播信息
 */

data class HomeLiveInfo(
		@SerializedName("live_id") var liveId: String, //13
		@SerializedName("live_title") var liveTitle: String, //OH卡
		@SerializedName("live_thumb") var liveThumb: String, //https://static.readymade.cn/Live/59cd16eb80f7c.jpg
		@SerializedName("live_url") var liveUrl: String //https://mp.weixin.qq.com/s?__biz=MzAxMDc3NjgyNw==&amp;amp;mid=2652804373&amp;amp;idx=2&amp;amp;sn=3dc0b0fb3db5461a240b914381e1bfc8&amp;amp;chksm=80a10c81b7d685975b4cafe4913deb501944f8a9243fda1f9f7102259d672cf68f8b8d0493eb#rd
)