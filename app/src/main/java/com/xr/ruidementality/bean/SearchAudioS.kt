package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/17.
 * Description:搜索出来的音频信息
 */

data class SearchAudioS(
		@SerializedName("title") var title: String, //萨提亚冥想——爱的信息
		@SerializedName("hour_long") var hourLong: String, //499
		@SerializedName("audio_id") var audioId: String, //305
		@SerializedName("audio_thumb") var audioThumb: String, //https://static.readymade.cn/Audio/Audio_cover/1487328636.jpg
		@SerializedName("album_id") var albumId: String //25
)