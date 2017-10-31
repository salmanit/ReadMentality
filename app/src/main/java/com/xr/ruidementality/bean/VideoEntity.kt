package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/9/30.
 * Description:视频列表实体类
 */

data class VideoEntity(
		@SerializedName("id") var id: String, //47
		@SerializedName("video_title") var videoTitle: String, //为什么恋爱这件小事令我们疯狂？
		@SerializedName("cover_url") var coverUrl: String, //http://10.0.2.52:8081/Uploads/Video/59310b6d6fb03.jpg
		@SerializedName("video_type_array") var videoTypeArray: ArrayList<String>,
		@SerializedName("hour_long") var hourLong: Long, //392
		@SerializedName("video_describe") var videoDescribe: String, //爱情之路的选择，受限于我们小时候学到的“什么是爱”。我们会根据从父母那里体验到的感觉来挑选伴侣，也引发了爱情里的种种问题——
		@SerializedName("serial_number") var serialNumber: String //01
)