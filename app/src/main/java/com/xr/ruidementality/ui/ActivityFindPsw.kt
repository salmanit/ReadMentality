package com.xr.ruidementality.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import com.xr.ruidementality.R
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.app.MyConstant
import com.xr.ruidementality.bean.UserInfo
import com.xr.ruidementality.custom.DefaultCustomCallBack
import com.xr.ruidementality.util.SpHelper
import com.xr.ruidementality.util.UtilNormal
import com.xr.ruidementality.util.UtilThis
import kotlinx.android.synthetic.main.activity_find_psw.*
import kotlinx.android.synthetic.main.layout_toolbar_normal.*
import org.simple.eventbus.EventBus

class ActivityFindPsw : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_psw)
        app_bar_layout.setBackgroundResource(R.color.login_toolbar_color)
        tv_title.text="找回密码"
        tv_get_auth.setOnClickListener {
            timer = UtilNormal.timer(tv_get_auth, 60)
            getAuthCode()
        }
        tv_register.setOnClickListener {
            findPsw()
        }
        iv_back.setOnClickListener { onBackPressed() }
    }
    var timer: CountDownTimer? = null
    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }
    fun getAuthCode() {
        var phone = phone_num_find.text.toString().trim()
        APIManager.sendSms(this,phone,2)
    }

    fun findPsw() {
        var phone = phone_num_find.text.toString().trim()
        var code = auth_num.text.toString().trim()
        var psw = psd_find.text.toString().trim()
        if (!UtilNormal.isMobileNO(phone)) {
            UtilThis.showHintToast(this, R.string.rd_login_phone_error)
            return
        }
        if (!UtilThis.isPasswordNo(psw)) {
            UtilThis.showHintToast(this, R.string.rd_login_psw_error)
            return
        }
        if (TextUtils.isEmpty(code)) {
            UtilThis.showHintToast(this, R.string.rd_login_code_error)
            return
        }

        APIManager.resetPsw(phone,code,psw,object:DefaultCustomCallBack<UserInfo>(){
            override fun onSuccess(t: UserInfo) {
                UtilThis.showHintToast(this@ActivityFindPsw, "密码重置成功")
                SpHelper.saveUserInfo(t)
                EventBus.getDefault().post(true, MyConstant.LOGINSTATUSCHANGE)
                finish()
            }

            override fun onError(type: Int, msg: String) {
                UtilThis.showHintToast(this@ActivityFindPsw, msg)
            }
        })
    }
}
