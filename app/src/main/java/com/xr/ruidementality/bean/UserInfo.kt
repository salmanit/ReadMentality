package com.xr.ruidementality.bean

import android.text.TextUtils
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/9/27.
 * Description:用户登陆返回的信息
 */

data class UserInfo(
        @SerializedName("id") var id: String, //1
        @SerializedName("user_name") var userName: String, //rduser_10001
        @SerializedName("user_nickname") var userNickname: String, //rduser_10001
        @SerializedName("user_mobile") var userMobile: String, //13524446830
        @SerializedName("headerimg_url") var headerimgUrl: String, //http://www.ruide.com/Uploads/Avatar/user.png
        @SerializedName("is_exist_password") var isExistPassword: Int, //1
        @SerializedName("user_money") var userMoney: String, //40.06
        @SerializedName("push_status") var pushStatus: Int //推送状态1:开启 2:关闭
)
{
    constructor():this("","","","","",0,"0",2)

    fun getShowName():String{
        if(TextUtils.isEmpty(userNickname)){
            return userName
        }else{
           return userNickname
        }
    }
}