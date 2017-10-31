package com.xr.ruidementality.util

import android.content.Context
import android.support.annotation.StringRes
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xr.ruidementality.R

/**
 * Created by Sage on 2017/9/26.
 * Description:只适合本工程的一些工具方法
 */
object UtilThis {

    var toast: Toast? = null
    fun showHintToast(context: Context, @StringRes hint: Int) {
        showHintToast(context, context.getString(hint))
    }

    fun showHintToast(context: Context, hint: String) {
        if (toast != null) {
            toast!!.cancel()
        }
        var toast = Toast(context)
        toast.view = LayoutInflater.from(context).inflate(R.layout.layout_show_hint_toast, null)
        var tv = toast.view.findViewById(R.id.tv_hit) as TextView
        tv.text = hint
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()

    }

    /**
     * 判断密码6~12位
     */
    fun isPasswordNo(password: String): Boolean {
        return password.length in 6..12
    }

    fun getShowTimeMS(time: Long): String {
        var minute = time / 60
        var seconds = time % 60
        return "${if (minute < 10) "0" else ""}${minute}'${if (seconds < 10) "0" else ""}${seconds}''"
    }
    fun getShowTimeColon(time: Long): String {
        var minute = time / 60
        var seconds = time % 60
        return "${if (minute < 10) "0" else ""}${minute}:${if (seconds < 10) "0" else ""}${seconds}"
    }
    fun getShowTimeMS_CN(time: Long): String {
        var minute = time / 60
        var seconds = time % 60
        return "${if (minute < 10) "0" else ""}${minute}分${if (seconds < 10) "0" else ""}${seconds}秒"
    }

    fun getUserId():String{
        var userID=SpHelper.getUserInfo().id
        if(TextUtils.isEmpty(userID)){
            userID=SpHelper.getUUID()
        }
        return userID
    }

    fun loadPic(iv:ImageView,url:String){
        try {
            Glide.with(iv.context).load(url).into(iv)
        } catch(e: Exception) {
        }
    }
    fun loadPic(iv:ImageView,url:String,errorRes:Int){
        try {
            val option=RequestOptions().error(errorRes)
            Glide.with(iv.context).load(url).apply(option).into(iv)
        } catch(e: Exception) {
        }
    }
    fun loadPic(iv:ImageView,resId:Int){
        try {
            Glide.with(iv.context).load(resId).into(iv)
        } catch(e: Exception) {
        }
    }


    fun getMSBySecond(time:Long):String{
        val mins = time / 60
        val see = time % 60
        var strmin = mins.toString() + ""
        var strsee = see.toString() + ""

        if (see < 10) {
            strsee = "0" + strsee
        }
        if (mins < 10) {
            strmin = "0" + strmin
        }
        return "$strmin'$strsee''"
    }
}