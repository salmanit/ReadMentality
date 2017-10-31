package com.xr.ruidementality.fragment.money

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.alipay.sdk.app.PayTask
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.xr.ruidementality.R
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.bean.OrderInfoFromService
import com.xr.ruidementality.custom.CustomCallBack
import com.xr.ruidementality.wxapi.WXPayEntryActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_my_money.*
import android.text.TextUtils
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ItemDetectorGridHV
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.bean.BaseDataMsg
import com.xr.ruidementality.bean.OrderSuccessInfo
import com.xr.ruidementality.bean.UserInfo
import com.xr.ruidementality.custom.DefaultCustomCallBack
import com.xr.ruidementality.fragment.FragmentBase
import com.xr.ruidementality.util.SpHelper
import com.xr.ruidementality.util.UtilNormal
import com.xr.ruidementality.util.UtilThis
import com.xr.ruidementality.wxapi.PayResult


/**
 * Created by Sage on 2017/10/20.
 * Description:我的瑞点页面
 */
class FragmentMyMoney : FragmentBase(), WXPayEntryActivity.WxPayCallBack {
    override fun onWxPay(code: Int, msg: String) {
        when (code) {
            BaseResp.ErrCode.ERR_OK -> {
                successOrder(out_trade_no)
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
                cancelOrder(out_trade_no)
            }
            else -> {
                UtilThis.showHintToast(activity, msg)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_my_money
    }

    var moneys = arrayListOf(6, 68, 108, 218)
    var out_trade_no: String = ""
    var payType = 3//支付类型 2.微信  3.支付宝
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        normalToolbar("我的瑞点")
        WXPayEntryActivity.setWxPay(this)
        tv_balance.text = SpHelper.getUserInfo().userMoney
        rg_pay.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_pay_ali -> payType = 3
                R.id.rb_pay_wx -> payType = 2
            }
        }
        tv_go_pay.setOnClickListener {
            createOrder()
        }
        layout_my_record.setOnClickListener {
            addFragment(FragmentMyPayOrder())
        }
        getUserInfo()

        rv_money.apply {
            layoutManager = GridLayoutManager(activity,2)
            val dpH=UtilNormal.dp2px(activity,10f).toInt()
            addItemDecoration(ItemDetectorGridHV(0,false).setTopBottom(true,true).setHVSpace(dpH,dpH))
            adapter = object : AdapterRv<Int>(moneys) {
                override fun layoutId(viewType: Int): Int {
                    return R.layout.item_my_money_choose
                }

                override fun handleData(holder: ViewHolderRv, position: Int, t: Int) {
                    holder.setText(R.id.tv_money, "${t}元")
                    holder.setText(R.id.tv_money_rd, "${t}瑞点")
                    holder.itemView.setBackgroundResource(
                            if (position != defaultCheck)
                                R.drawable.shape_bg_white_corner6
                            else
                                R.drawable.shape_bg_white_corner6_stroke_theme)
                    holder.itemView.setOnClickListener {
                        if(position!=defaultCheck){
                            val old=defaultCheck
                            defaultCheck=position
                            notifyItemChanged(old)
                            notifyItemChanged(position)
                        }
                    }
                }
            }
        }
    }

    var defaultCheck = 2;//默认的充值金额
    override fun onDestroy() {
        super.onDestroy()
        WXPayEntryActivity.setWxPay(null)
    }

    fun getUserInfo() {
        APIManager.getUserInfo(object : DefaultCustomCallBack<UserInfo>() {
            override fun onSuccess(t: UserInfo) {
                SpHelper.saveUserInfo(t)
                tv_balance.text = t.userMoney
            }

            override fun onError(type: Int, msg: String) {

            }
        })
    }

    fun createOrder() {
        APIManager.createOrder(payType, 0.01f, object : CustomCallBack<OrderInfoFromService> {
            override fun onComplete() {

            }

            override fun onSuccess(t: OrderInfoFromService) {
                if (payType == 3) {
                    payByAli(t)
                } else if (payType == 2) {
                    payByWX(t)
                }
            }

            override fun onError(type: Int, msg: String) {

            }
        })
    }

    fun payByWX(t: OrderInfoFromService) {
        val req = PayReq()
        req.appId = t.appid
        req.partnerId = t.partnerid
        req.prepayId = t.prepayid
        req.nonceStr = t.noncestr
        req.timeStamp = "${t.timestamp}"
        req.packageValue = t.packageX
        req.sign = t.sign
        // 将该app注册到微信
        val msgApi = WXAPIFactory.createWXAPI(activity, WXPayEntryActivity.WECHAT_APPID)
        msgApi.sendReq(req)
    }

    fun payByAli(t: OrderInfoFromService) {
        Observable.create<Map<String, String>> {
            var result = PayTask(activity).payV2(t.signString, true)
            println("payByAli===========$result")
            it.onNext(result)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    println("支付结果=====$it")
                    val payResult = PayResult(it)
                    /**
                    对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultStatus = payResult.resultStatus
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        UtilThis.showHintToast(activity, "支付成功")
                        successOrder(t.outTradeNo)
                    } else if (TextUtils.equals(resultStatus, "8000") || TextUtils.equals(resultStatus, "6004")) {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        UtilThis.showHintToast(activity, "支付结果确认中")
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        cancelOrder(t.outTradeNo)
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        UtilThis.showHintToast(activity, "重复请求")
                    } else {
                        UtilThis.showHintToast(activity, "支付失败")//取消支付，没有网络等情况
                    }
                }
    }

    fun cancelOrder(out_trade_no: String) {
        APIManager.cancelOrder(out_trade_no, object : DefaultCustomCallBack<BaseDataMsg>() {
            override fun onSuccess(t: BaseDataMsg) {
                getUserInfo()
            }

            override fun onError(type: Int, msg: String) {

            }
        })
    }

    fun successOrder(out_trade_no: String) {
        APIManager.notifyOrderSuccess(out_trade_no, object : DefaultCustomCallBack<OrderSuccessInfo>() {
            override fun onSuccess(t: OrderSuccessInfo) {
                getUserInfo()
            }

            override fun onError(type: Int, msg: String) {

            }
        })
    }
    //支付宝支付结果resultStatus  https://docs.open.alipay.com/204/105301/
}