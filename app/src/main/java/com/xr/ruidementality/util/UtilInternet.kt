package com.xr.ruidementality.util

import android.content.Context
import android.telephony.TelephonyManager
import android.net.ConnectivityManager



/**
 * Created by Sage on 2017/9/27.
 * Description:网络状态判断
 */
object UtilInternet{
    /**
     * 判断是否有网络连接
     */
    fun isNetworkConnected(context: Context): Boolean {
        if (context != null) {
            val mConnectivityManager = context!!
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }


    /**
     * 判断WIFI网络是否可用
     */
    fun isWifiConnected(context: Context): Boolean {
        if (context != null) {
            val mConnectivityManager = context!!
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable
            }
        }
        return false
    }


    /**
     * 判断MOBILE网络是否可用
     */
    fun isMobileConnected(context: Context): Boolean {
        if (context != null) {
            val mConnectivityManager = context!!
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable
            }
        }
        return false
    }


    /**
     * 获取当前网络连接的类型信息
     */
    fun getConnectedType(context: Context?): Int {
        if (context != null) {
            val mConnectivityManager = context!!
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
                return mNetworkInfo.type
            }
        }
        return -1
    }


    /**
     * 获取当前的网络状态 ：没有网络0：WIFI网络1：3G网络2：2G网络3
     */
    fun getAPNType(context: Context): Int {
        var netType = 0
        val connMgr = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo ?: return netType
        val nType = networkInfo.type
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1// wifi
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            val nSubType = networkInfo.subtype
            val mTelephony = context
                    .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming) {
                netType = 2// 3G
            } else {
                netType = 3// 2G
            }
        }
        return netType
    }
}