package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/25.
 * Description:历史收听实体类
 */

data class HistoryListen(
		@SerializedName("audio_id") var audioId: String, //804
		@SerializedName("audio_name") var audioName: String, //催眠故事 | 夏娃之子
		@SerializedName("audio_last_play_time") var audioLastPlayTime: String, //1556
		@SerializedName("album") var album: AlbumEntity?,
		@SerializedName("audio_file_url") var audioFileUrl: String //https://readymade.oss-cn-shanghai.aliyuncs.com/audio/58/1492504544_195.mp3
)