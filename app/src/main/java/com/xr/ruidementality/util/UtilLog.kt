package com.xr.ruidementality.util

import android.util.Log
import com.xr.ruidementality.BuildConfig

/**
 * Created by Sage on 2017/10/16.
 * Description:
 */
 object UtilLog {

    var logI = BuildConfig.DEBUG
    open fun i(msg: String) {
        if (logI) {
            Log.i("UtilLog", "$msg")
        }
    }
}