package com.xr.ruidementality.fragment.money

import android.text.Html
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.bean.ReChargeRecord
import com.xr.ruidementality.fragment.FragmentBaseRefresh

/**
 * Created by Sage on 2017/10/20.
 * Description:
 */
class FragmentMyPayOrder : FragmentBaseRefresh<ReChargeRecord>() {

    override fun getLayout(): Int {
        return R.layout.fragment_base_refresh_have_title
    }

    override fun changeSomething() {
        normalToolbar("充值记录")
        adapterBase = object : AdapterRv<ReChargeRecord>() {
            override fun layoutId(viewType: Int): Int {
                return R.layout.item_fragment_my_pay_order
            }

            override fun handleData(holder: ViewHolderRv, position: Int, t: ReChargeRecord) {
                holder.setText(R.id.tv_recharge_type, t.rechargeType)
                holder.setText(R.id.tv_recharge_time, t.rechargeTime)
                holder.setText(R.id.tv_recharge_money, Html.fromHtml(getString(R.string.format_recharge_money_show, t.rechargeMoney)))

            }
        }
    }

    override fun postHttp() {
        APIManager.getRechargeOrder(page, pageSize, callback)
    }
}