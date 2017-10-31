package com.xr.ruidementality.app

/**
 * Created by Sage on 2017/10/12.
 * Description:存储一些全局变量
 */
object MyConstant {
    var currentAudioTypeId="0" //当前选中的音频分类id
    var currentVideoTypeId="0"
    var currentLiveTypeId="0"

    var LOGINSTATUSCHANGE="loginStatusChange"//登陆状态发生变化的时候发送广播，用来刷新数据
}