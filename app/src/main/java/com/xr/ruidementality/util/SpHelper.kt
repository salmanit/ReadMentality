package com.xr.ruidementality.util

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.xr.ruidementality.app.MyApplication
import com.xr.ruidementality.bean.UserInfo

/**
 * Created by Sage on 2017/9/26.
 * Description:
 */
object SpHelper {
    var NAME_SP_DEFAULT = "read_mentality"//sp的名字
    val KEY_VERSION_APP = "key_version_app"//保存的app版本号
    var KEY_UUID = "key_uuid"//本机唯一的机器码，可能是个随机数
    val KEY_USERINFO = "key_userInfo"//保存用户信息
    val KEY_NOT_WIFI_PROMPUT = "key_not_wifi_promput"//非wifi下视频提醒，默认是开的
    val KEY_MSG_PUSH_TOAST = "key_msg_push_toast"//消息推送

    fun saveUserInfo(userInfo: UserInfo) {
        putString(KEY_USERINFO, Gson().toJson(userInfo))
    }

    fun getUserInfo(): UserInfo {
        var info = getString(KEY_USERINFO, "")
        if (TextUtils.isEmpty(info)) {
            return UserInfo()
        }
        try {
            return Gson().fromJson(info, UserInfo::class.java)
        } catch(e: Exception) {
        }
        return UserInfo()
    }

    fun getUUID(): String {
        var uuid = getString(KEY_UUID, "")
        if (TextUtils.isEmpty(uuid)) {
            uuid = DeviceUUIDFactory.getUUID(MyApplication.myApp)
            putString(KEY_UUID, uuid)
        }
        return uuid
    }

    fun getVersion(): String {
        return getString(KEY_VERSION_APP, "1.0")
    }

    fun saveVersion(version: String) {
        putString(KEY_VERSION_APP, version)
    }

    fun getWifiToast(): Boolean {
        return getBoolean(KEY_NOT_WIFI_PROMPUT, true)
    }

    fun saveWifiToast(value: Boolean) {
        putBoolean(KEY_NOT_WIFI_PROMPUT, value)
    }

    fun getMsgPush(): Boolean {
        return getBoolean(KEY_MSG_PUSH_TOAST, true)
    }

    fun saveMsgPush(value: Boolean) {
        putBoolean(KEY_MSG_PUSH_TOAST, value)
    }

    fun getString(key: String, defaultValue: String): String {
        return getDefaultSP().getString(key, "")
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return getDefaultSP().getLong(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return getDefaultSP().getInt(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return getDefaultSP().getBoolean(key, defaultValue)
    }


    fun putString(key: String, value: String) {
        getDefaultSP().edit().putString(key, value).commit()
    }

    fun putLong(key: String, value: Long) {
        getDefaultSP().edit().putLong(key, value).commit()
    }

    fun putInt(key: String, value: Int) {
        getDefaultSP().edit().putInt(key, value).commit()
    }

    fun putBoolean(key: String, value: Boolean) {
        getDefaultSP().edit().putBoolean(key, value).commit()
    }

    fun getDefaultSP(): SharedPreferences {
        return MyApplication.myApp.getSharedPreferences(NAME_SP_DEFAULT, Context.MODE_PRIVATE)
    }
}