package com.xr.ruidementality.fragment.login

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import com.xr.ruidementality.R
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.app.MyConstant
import com.xr.ruidementality.bean.UserInfo
import com.xr.ruidementality.custom.DefaultCustomCallBack
import com.xr.ruidementality.custom.TextWatcherSimple
import com.xr.ruidementality.fragment.FragmentBase
import com.xr.ruidementality.ui.ActivityRegisterProtocol
import com.xr.ruidementality.util.SpHelper
import com.xr.ruidementality.util.UtilNormal
import com.xr.ruidementality.util.UtilThis
import kotlinx.android.synthetic.main.fragment_register.*
import org.simple.eventbus.EventBus

/**
 * Created by Sage on 2017/9/26.
 * Description: 注册模块
 */
@Deprecated("")
class FragmentRegister : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_register
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        phone_num.addTextChangedListener(object : TextWatcherSimple() {
            override fun afterTextChanged(s: Editable) {
                tv_get_auth_register.isEnabled = s.toString().isNotEmpty()
            }
        })
        tv_get_auth_register.setOnClickListener {
            timer2 = UtilNormal.timer(tv_get_auth_register, 60)
            getAuthCode()
        }
        tv_register.setOnClickListener { register() }
        tv_protocol.setOnClickListener { startActivity(Intent(activity,ActivityRegisterProtocol::class.java)) }
    }

    var timer2: CountDownTimer? = null
    override fun onDestroy() {
        super.onDestroy()
        if (timer2 != null) {
            timer2!!.cancel()
            timer2 = null
        }
    }

    fun getAuthCode() {
        var phone = phone_num.text.toString().trim()
        APIManager.sendSms(activity,phone,1)
    }

    fun register() {
        var phone = phone_num.text.toString().trim()
        var code = auth_num_register.text.toString().trim()
        var psw = et_psd_register.text.toString().trim()
        if (!UtilNormal.isMobileNO(phone)) {
            UtilThis.showHintToast(activity, R.string.rd_login_phone_error)
            return
        }
        if (!UtilThis.isPasswordNo(psw)) {
            UtilThis.showHintToast(activity, R.string.rd_login_psw_error)
            return
        }
        if (TextUtils.isEmpty(code)) {
            UtilThis.showHintToast(activity, R.string.rd_login_code_error)
            return
        }
        if(!cb_agree.isChecked){
            UtilThis.showHintToast(activity, R.string.rd_register_protocol_error)
            return
        }

        APIManager.register(phone,code,psw,object : DefaultCustomCallBack<UserInfo>() {
            override fun onSuccess(t: UserInfo) {
                UtilThis.showHintToast(activity, "注册成功")
                SpHelper.saveUserInfo(t)
                EventBus.getDefault().post(true, MyConstant.LOGINSTATUSCHANGE)
            }

            override fun onError(type: Int, msg: String) {
                UtilThis.showHintToast(activity, msg)
            }
        })
    }

}