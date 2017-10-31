package com.xr.ruidementality.ui

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.xr.ruidementality.R
import com.xr.ruidementality.util.SpHelper
import com.xr.ruidementality.util.UtilNormal
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ActivityWelcome : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribe {

            goWhere()
        }
    }

    fun goWhere() {
        var version = UtilNormal.getAppVersionNow()
        if (SpHelper.getVersion() < version) {
            SpHelper.saveVersion(version)
            startActivity(Intent(this, ActivityGuide::class.java))
        } else {
            startActivity(Intent(this, ActivityHome::class.java))
        }
        finish()
    }
}
