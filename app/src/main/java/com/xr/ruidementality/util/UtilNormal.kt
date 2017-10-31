package com.xr.ruidementality.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import com.xr.ruidementality.app.MyApplication
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView


/**
 * Created by Sage on 2017/9/26.
 * Description:通用的工具方法
 */
object UtilNormal {

    fun getContext(): Context {
        return MyApplication.myApp
    }

    /**获取版本号*/
    fun getAppVersionNow(): String {
        return getContext().packageManager.getPackageInfo(getContext().packageName, 0).versionName
    }

    /**
     * 验证手机格式
     */
    fun isMobileNO(mobiles: String): Boolean {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或或7或8，其他位置的可以为0-9
		 */
        val telRegex = "[1][3578]\\d{9}"// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex.toRegex())
    }

    fun timer(tv_code: TextView, time: Int): CountDownTimer {
        val original_show = tv_code.text.toString()
        tv_code.isClickable = false
        val timer = object : CountDownTimer((time * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tv_code.text = "${(millisUntilFinished / 1000)} s"
            }

            override fun onFinish() {
                tv_code.text = original_show
                tv_code.isClickable = true
            }
        }
        timer.start()
        return timer
    }

    fun dp2px(context: Context, dp: Float): Float {
        return context.resources.displayMetrics.density * dp
    }

    fun hideSoftInput(activity: Activity) {
        var view = activity.currentFocus
        if (view == null) view = View(activity)
        var imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideSoftInput(activity: Activity, editText: EditText) {
        var imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun isNetworkConnected(): Boolean {
        try {
            val mConnectivityManager = MyApplication.myApp
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        } catch(e: Exception) {
        }
        return false
    }
}