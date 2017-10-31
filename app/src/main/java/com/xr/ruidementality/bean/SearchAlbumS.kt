package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/17.
 * Description:搜索出来的专辑信息
 */

data class SearchAlbumS(
		@SerializedName("title") var title: String, //心灵SPA
		@SerializedName("album_id") var albumId: String, //9
		@SerializedName("album_thumb") var albumThumb: String //https://static.readymade.cn/Album/1488361695.jpg
)