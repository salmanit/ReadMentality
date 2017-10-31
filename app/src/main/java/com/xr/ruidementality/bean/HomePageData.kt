package com.xr.ruidementality.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/11.
 * Description:首页返回的列表数据
 */

data class HomePageData(
        //这部分是音频数据
        @SerializedName("audio_id") var audioId: String, //53
        @SerializedName("audio_title") var audioTitle: String, //4-2.什么是认知心理学
        @SerializedName("audio_point") var audioPoint: String, //0.5
        @SerializedName("audio_thumb") var audioThumb: String, //http://10.0.2.52:8081/Uploads/Position/58d4c0f3c4dbd.jpg
        @SerializedName("audio_hour_long") var audioHourLong: Long, //184
        @SerializedName("audio_file_url") var audioFileUrl: String,
        @SerializedName("album_id") var albumId: String, //1
        @SerializedName("subscribe_status") var subscribeStatus: Int, //1
        @SerializedName("album_type_name") var albumTypeName: ArrayList<String>,
        @SerializedName("audio_introduction") var audioIntroduction: String, //什么是认知心理学，研究“知”的心理学
        @SerializedName("album_thumb") var albumThumb: String, //http://10.0.2.52:8081/Uploads/Album/592e87c2aaeac.jpg
        @SerializedName("album_title") var albumTitle: String, //心理学玩的小技巧
        @SerializedName("album_introduction") var albumIntroduction: String, //日本百万册畅销心理学作家原田玲仁的代表作，日本话题度爆表的好玩心理学。166个趣味心理学话题，逸闻趣事式讲解，只看漫画也一样学得会...
        @SerializedName("anchor_name") var anchorName: String, //Lilian
        //这两个是公共数据，用来区分数据类型的
        @SerializedName("position_type") var positionType: Int, //1:音频模块 2:视频模块 3:图文
        @SerializedName("module_name") var moduleName: String, //音频模块
        //下面是视频返回的数据
        @SerializedName("video_id") var videoId: String, //33
        @SerializedName("video_title") var videoTitle: String, //我们为什么会做梦？
        @SerializedName("cover_thumb") var coverThumb: String, //http://10.0.2.52:8081/Uploads/Video/58e4a06211a49.jpg
        @SerializedName("link_url") var linkUrl: String, //http://10.0.2.52:8081/api/video/web_display?device=1&id=33
        @SerializedName("video_type_name") var videoTypeName: ArrayList<String>,
        @SerializedName("hour_long") var hourLong: Long, //340
        @SerializedName("video_describe") var videoDescribe: String //二十世纪初期，我们对梦的理解发生了哪些变化呢？

)
