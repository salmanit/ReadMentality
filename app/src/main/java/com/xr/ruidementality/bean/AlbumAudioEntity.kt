package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/18.
 * Description:专辑下的音频数据
 */

data class AlbumAudioEntity(
		@SerializedName("update_time") var updateTime: String, //2016-11-10 09:23:59
		@SerializedName("introduction") var introduction: String, //音频见解一下
		@SerializedName("web_url") var webUrl: String,
		@SerializedName("audio_cover_url") var audioCoverUrl: String, //http://10.0.2.52:8081/Uploads/Audio/Audio_cover/1449047949_788.jpg
		@SerializedName("id") var id: String, //78
		@SerializedName("audio_title") var audioTitle: String, //1.从董卿痛哭谈缺失的父爱
		@SerializedName("audio_hour_long") var audioHourLong: Long, //528
		@SerializedName("audio_point") var audioPoint: String, //40.0
		@SerializedName("audio_file_url") var audioFileUrl: String, //http://10.0.2.52:8081/Uploads/Audio/2/1464675743_403.mp3
		@SerializedName("audio_play_number") var audioPlayNumber: Int, //372
		@SerializedName("audo_file_size") var audoFileSize: String, //8455314
		@SerializedName("buy_status") var buyStatus: Int //1
)