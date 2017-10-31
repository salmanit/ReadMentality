package com.xr.ruidementality.fragment.login

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.TabLayout
import android.text.Editable
import android.text.TextUtils
import android.view.View
import com.xr.ruidementality.BuildConfig
import com.xr.ruidementality.R
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.app.MyConstant
import com.xr.ruidementality.bean.UserInfo
import com.xr.ruidementality.custom.DefaultCustomCallBack
import com.xr.ruidementality.custom.TextWatcherSimple
import com.xr.ruidementality.fragment.FragmentBase
import com.xr.ruidementality.ui.ActivityFindPsw
import com.xr.ruidementality.ui.ActivityHome
import com.xr.ruidementality.util.SpHelper
import com.xr.ruidementality.util.UtilNormal
import com.xr.ruidementality.util.UtilThis
import kotlinx.android.synthetic.main.fragment_login.*
import org.simple.eventbus.EventBus

/**
 * Created by Sage on 2017/9/26.
 * Description:登录模块，
 */
@Deprecated("已经合并到activityli里")
class FragmentLogin : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_login
    }

    var type = 0//0默认的密码登陆，1验证码快速登陆
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tab_login.addTab(tab_login.newTab().setText(getString(R.string.rd_login_type1)))
        tab_login.addTab(tab_login.newTab().setText(getString(R.string.rd_login_type2)))
        tab_login.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        type = 0
                        get_code_rl.visibility = View.GONE
                        tv_find_psd.visibility = View.VISIBLE
                        psd_login.visibility = View.VISIBLE
                        val leftDrawable = resources.getDrawable(R.mipmap.rd_login_phone)
                        leftDrawable.setBounds(0, 0, leftDrawable.minimumWidth, leftDrawable.minimumHeight)
                        et_login_phone.setCompoundDrawables(leftDrawable, null, null, null)
                    }
                    1 -> {
                        type = 1
                        get_code_rl.visibility = View.VISIBLE
                        tv_find_psd.visibility = View.GONE
                        psd_login.visibility = View.GONE
                        et_login_phone.setCompoundDrawables(null, null, null, null)
                    }
                }
            }
        })
        et_login_phone.addTextChangedListener(object : TextWatcherSimple() {
            override fun afterTextChanged(s: Editable) {
                tv_get_auth.isEnabled = s.toString().isNotEmpty()
            }
        })
        tv_get_auth.setOnClickListener {
            timer = UtilNormal.timer(tv_get_auth, 60)
            getAuthCode()
        }
        tv_find_psd.setOnClickListener {
            startActivity(Intent(activity, ActivityFindPsw::class.java))
        }

        tv_login.setOnClickListener {
            login()
        }
        if (BuildConfig.DEBUG) {
            et_login_phone.setText("13795492180")
            psd_login.setText("555555")
        }
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
        val phone = et_login_phone.text.toString().trim()
        APIManager.sendSms(activity, phone, 3)
    }

    fun login() {
        var phone = et_login_phone.text.toString().trim()
        var code = auth_num.text.toString().trim()
        var psw = psd_login.text.toString().trim()
        if (!UtilNormal.isMobileNO(phone)) {
            UtilThis.showHintToast(activity, R.string.rd_login_phone_error)
            return
        }
        when (type) {
            0 -> {
                if (!UtilThis.isPasswordNo(psw)) {
                    UtilThis.showHintToast(activity, R.string.rd_login_psw_error)
                    return
                }
                loginByPsw(phone, psw)
            }
            1 -> {
                if (TextUtils.isEmpty(code)) {
                    UtilThis.showHintToast(activity, R.string.rd_login_code_error)
                    return
                }
                loginByCode(phone, code)
            }
        }


    }

    fun loginByPsw(phone: String, psw: String) {
        APIManager.loginByPsw(phone, psw, loginCallBack)
    }

    fun loginByCode(phone: String, code: String) {
        APIManager.loginByCode(phone, code, loginCallBack)
    }

    var loginCallBack = object : DefaultCustomCallBack<UserInfo>() {
        override fun onSuccess(t: UserInfo) {
            UtilThis.showHintToast(activity, "登陆成功")
            SpHelper.saveUserInfo(t)
            EventBus.getDefault().post(true,MyConstant.LOGINSTATUSCHANGE)

        }

        override fun onError(type: Int, msg: String) {
            UtilThis.showHintToast(activity, msg)
        }
    }


}