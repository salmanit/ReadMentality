package com.xr.ruidementality.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by Sage on 2017/10/20.
 * Description:从服务器获取的订单信息
 */

data class OrderInfoFromService(
		@SerializedName("sign_string") var signString: String, //app_id=2017
		@SerializedName("out_trade_no") var outTradeNo: String, //201710201202374600
		@SerializedName("appid") var appid: String, //wxb18f4b1547a238a1
		@SerializedName("partnerid") var partnerid: String, //1388349002
		@SerializedName("prepayid") var prepayid: String, //wx2017102012034548dd7aac160243286710
		@SerializedName("package") var packageX: String, //Sign=WXPay
		@SerializedName("noncestr") var noncestr: String, //fbwd2hu5o08gqb021rt9f3z3q41j37gn
		@SerializedName("timestamp") var timestamp: Int, //1508472158
		@SerializedName("sign") var sign: String //3ED215FA91EABE6018BA6176F52CA6B9
)