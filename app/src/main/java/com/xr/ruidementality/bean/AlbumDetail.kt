package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/19.
 * Description:专辑详情数据
 */


data class AlbumDetail(
		@SerializedName("id") var id: Int, //90
		@SerializedName("album_title") var albumTitle: String, //女神养成计划
		@SerializedName("album_cover_url") var albumCoverUrl: String, //https://static.readymade.cn/Album/59a507474a17a.jpg
		@SerializedName("album_play_number") var albumPlayNumber: Int, //0
		@SerializedName("anchor_name") var anchorName: String, //伊莎贝
		@SerializedName("update_time") var updateTime: String, //2017-08-29
		@SerializedName("album_introduction") var albumIntroduction: String, //某网站曾票选网友心目中最美丽的
		@SerializedName("category_array") var categoryArray: ArrayList<String>?,
		@SerializedName("album_audio_number") var albumAudioNumber: Int, //0
		@SerializedName("album_is_use") var albumIsUse: String, //0
		@SerializedName("album_end_money") var albumEndMoney: Int, //0
		@SerializedName("album_end_status") var albumEndStatus: Int, //0
		@SerializedName("album_summarize") var albumSummarize: String, //成为一个漂亮的女人，只要坚持100天
		@SerializedName("subscribe_status") var subscribeStatus: Int, //0
		@SerializedName("all_audio_buy_status") var allAudioBuyStatus: Int //1
//		,
//		@SerializedName("id") var id: Int, //1
//@SerializedName("album_title") var albumTitle: String, //心理学玩的小技巧
//@SerializedName("album_cover_url") var albumCoverUrl: String, //https://static.readymade.cn/Album/592e87c2aaeac.jpg
//@SerializedName("category_array") var categoryArray: ArrayList<String>,
//@SerializedName("album_end_money") var albumEndMoney: String, //4.00
//@SerializedName("album_end_status") var albumEndStatus: Int, //1
//@SerializedName("album_summarize") var albumSummarize: String, //测试一下而已。。
//@SerializedName("album_audio_money") var albumAudioMoney: String //3.0
){
	fun getCatalog():String{
		var result=""
		if(categoryArray!=null&&categoryArray!!.isNotEmpty()){
			( categoryArray!!.indices).forEach { result=result+ categoryArray!![it] +" " }
		}
		return result
	}
}