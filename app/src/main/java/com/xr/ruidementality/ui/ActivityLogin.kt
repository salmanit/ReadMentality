package com.xr.ruidementality.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
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
import com.xr.ruidementality.fragment.login.FragmentLogin
import com.xr.ruidementality.fragment.login.FragmentRegister
import com.xr.ruidementality.util.SpHelper
import com.xr.ruidementality.util.UtilLog
import com.xr.ruidementality.util.UtilNormal
import com.xr.ruidementality.util.UtilThis
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.layout_toolbar_normal.*
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber

class ActivityLogin : ActivityBase() {
    var type = 0//0默认的密码登陆，1验证码快速登陆
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app_bar_layout.setBackgroundResource(R.color.login_toolbar_color)
        setCurrent(intent.getIntExtra("position", 0))
        tv_right.setOnClickListener {
            when(va_login.displayedChild){
                0->{
                    va_login.showNext()
                    setCurrent(1)
                }
                1->{
                    va_login.showPrevious()
                    setCurrent(0)
                }
            }
        }
        iv_back.setOnClickListener { onBackPressed() }

        initLogin()
        initRegister()
    }

    fun initRegister(){
        phone_num.addTextChangedListener(object : TextWatcherSimple() {
            override fun afterTextChanged(s: Editable) {
                tv_get_auth_register.isEnabled = s.toString().isNotEmpty()
            }
        })
        tv_get_auth_register.setOnClickListener {
            timer2 = UtilNormal.timer(tv_get_auth_register, 60)
            getAuthCode(1)
        }
        tv_register.setOnClickListener { register() }
        tv_protocol.setOnClickListener { startActivity(Intent(this@ActivityLogin,ActivityRegisterProtocol::class.java)) }
    }
    fun initLogin(){
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
            getAuthCode(3)
        }
        tv_find_psd.setOnClickListener {
            startActivity(Intent(this@ActivityLogin, ActivityFindPsw::class.java))
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
    var timer2: CountDownTimer? = null
    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        if (timer2 != null) {
            timer2!!.cancel()
            timer2 = null
        }
    }

    fun setCurrent(position: Int) {
        when (position) {
            0 -> {
                tv_title.setText(R.string.rd_login)
                tv_right.setText(R.string.rd_register)
            }
            1 -> {
                tv_title.setText(R.string.rd_register)
                tv_right.setText(R.string.rd_login)
            }
        }
    }

    override fun onBackPressed() {
        when(va_login.displayedChild){
            0->{
                super.onBackPressed()
            }
            1->{
                va_login.showPrevious()
                setCurrent(0)
            }
        }

    }
    override fun eventBusEnable(): Boolean {
        return true
    }

    @Subscriber(tag = "loginStatusChange")
    fun closeActivity(isLogin: Boolean) {
        //进入这个方法表示登陆成功，或者是注册成功，或者是重置密码成功，这时候刷新登陆状态、
        onBackPressed()
    }

    fun getAuthCode(type:Int) {
        val phone = et_login_phone.text.toString().trim()
        APIManager.sendSms(this@ActivityLogin, phone, type)
    }

    fun login() {
        var phone = et_login_phone.text.toString().trim()
        var code = auth_num.text.toString().trim()
        var psw = psd_login.text.toString().trim()
        if (!UtilNormal.isMobileNO(phone)) {
            UtilThis.showHintToast(this@ActivityLogin, R.string.rd_login_phone_error)
            return
        }
        when (type) {
            0 -> {
                if (!UtilThis.isPasswordNo(psw)) {
                    UtilThis.showHintToast(this@ActivityLogin, R.string.rd_login_psw_error)
                    return
                }
                loginByPsw(phone, psw)
            }
            1 -> {
                if (TextUtils.isEmpty(code)) {
                    UtilThis.showHintToast(this@ActivityLogin, R.string.rd_login_code_error)
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
            UtilThis.showHintToast(this@ActivityLogin, "登陆成功")
            SpHelper.saveUserInfo(t)
            EventBus.getDefault().post(true, MyConstant.LOGINSTATUSCHANGE)

        }

        override fun onError(type: Int, msg: String) {
            UtilThis.showHintToast(this@ActivityLogin, msg)
        }
    }


    fun register() {
        var phone = phone_num.text.toString().trim()
        var code = auth_num_register.text.toString().trim()
        var psw = et_psd_register.text.toString().trim()
        if (!UtilNormal.isMobileNO(phone)) {
            UtilThis.showHintToast(this@ActivityLogin, R.string.rd_login_phone_error)
            return
        }
        if (!UtilThis.isPasswordNo(psw)) {
            UtilThis.showHintToast(this@ActivityLogin, R.string.rd_login_psw_error)
            return
        }
        if (TextUtils.isEmpty(code)) {
            UtilThis.showHintToast(this@ActivityLogin, R.string.rd_login_code_error)
            return
        }
        if(!cb_agree.isChecked){
            UtilThis.showHintToast(this@ActivityLogin, R.string.rd_register_protocol_error)
            return
        }

        APIManager.register(phone,code,psw,object : DefaultCustomCallBack<UserInfo>() {
            override fun onSuccess(t: UserInfo) {
                UtilThis.showHintToast(this@ActivityLogin, "注册成功")
                SpHelper.saveUserInfo(t)
                EventBus.getDefault().post(true, MyConstant.LOGINSTATUSCHANGE)
            }

            override fun onError(type: Int, msg: String) {
                UtilThis.showHintToast(this@ActivityLogin, msg)
            }
        })
    }

}
