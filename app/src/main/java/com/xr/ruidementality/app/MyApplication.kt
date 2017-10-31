package com.xr.ruidementality.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.xr.ruidementality.BuildConfig
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.xr.ruidementality.R

/**
 * Created by Sage on 2017/9/26.
 * Description:
 */
class MyApplication : Application() {

    companion object {
        lateinit var myApp: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        myApp = this
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
        SmartRefreshLayout.setDefaultRefreshHeaderCreater { context, layout ->
            layout.setPrimaryColorsId(R.color.white, R.color.color96);//全局设置主题颜色,第一个是背景色,第二个是文字箭头颜色
            return@setDefaultRefreshHeaderCreater ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreater { context, layout ->
            //指定为经典Footer，默认是 BallPulseFooter
            return@setDefaultRefreshFooterCreater ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
        }
    }

}