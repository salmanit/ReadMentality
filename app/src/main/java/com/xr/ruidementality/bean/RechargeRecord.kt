package com.xr.ruidementality.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/20.
 * Description:
 */

data class ReChargeRecord(
		@SerializedName("recharge_type") var rechargeType: String, //支付宝支付
		@SerializedName("recharge_time") var rechargeTime: String, //2016-11-21 12:00:07
		@SerializedName("recharge_money") var rechargeMoney: String, //0.01
		@SerializedName("recharge_status") var rechargeStatus: String //success
)