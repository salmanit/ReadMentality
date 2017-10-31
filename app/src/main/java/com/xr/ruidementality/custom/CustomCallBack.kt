package com.xr.ruidementality.custom

/**
 * Created by Sage on 2017/9/27.
 * Description:自定义接口回调，包括成功和失败，目的是为了方便解耦网络请求库，万一以后换新的请求库，就在工具里替换就行了。代码里不用查找替换了
 */
interface CustomCallBack<T> {
    fun onSuccess(t:T)

    fun onError(type:Int,msg:String)

    fun onComplete()
}