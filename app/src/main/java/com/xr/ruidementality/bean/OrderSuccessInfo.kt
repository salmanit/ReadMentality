package com.xr.ruidementality.bean

/**
 * Created by Sage on 2017/10/20.
 * Description:通知服务器订单支付成功后，后台返回的信息,充值金额和用户余额
 */
data class OrderSuccessInfo(var recharge_money: String, var user_money: String)
