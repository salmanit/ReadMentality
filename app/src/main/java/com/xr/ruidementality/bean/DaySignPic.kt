package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/11.
 * Description:
 */

data class DaySignPic(
		@SerializedName("adfile") var adfile: String, //https://static.readymade.cn/adPic/59dc7cc6286b3.jpg
		@SerializedName("id") var id: String, //277
		@SerializedName("upvote_status") var upvoteStatus: Int //1:已点赞 0:未点赞
)