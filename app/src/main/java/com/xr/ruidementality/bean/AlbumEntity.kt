package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/9/29.
 * Description:音频列表页
 */

data class AlbumEntity(
		@SerializedName("id") var id: Int, //1
		@SerializedName("album_title") var albumTitle: String, //心理学玩的小技巧
		@SerializedName("album_cover_url") var albumCoverUrl: String, //https://static.readymade.cn/Album/592e87c2aaeac.jpg
		@SerializedName("category_array") var categoryArray: ArrayList<String>,
		@SerializedName("album_end_money") var albumEndMoney: String, //4.00
		@SerializedName("album_end_status") var albumEndStatus: Int, //1
		@SerializedName("album_summarize") var albumSummarize: String, //测试一下而已。。
		@SerializedName("album_audio_money") var albumAudioMoney: String //3.0
)