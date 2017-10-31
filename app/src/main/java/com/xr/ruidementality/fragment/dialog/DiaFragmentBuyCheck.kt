package com.xr.ruidementality.fragment.dialog

import android.os.Bundle
import android.view.View
import com.xr.ruidementality.R
import kotlinx.android.synthetic.main.dia_fragment_buy_check.*

/**
 * Created by Sage on 2017/10/19.
 * Description:购买确认框
 */
class DiaFragmentBuyCheck : DialogFragmentBase() {
    override fun getLayout(): Int {
        return R.layout.dia_fragment_buy_check
    }

    var title = ""
    var moneyNeed = 0f
    var moneyHave = 0f
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_cancel.setOnClickListener {
            dismiss()
        }
        iv_cancel.setOnClickListener {
            dismiss()
        }
        tv_ok.setOnClickListener { v ->
            dismiss()
            if (okClickListener != null) {
                okClickListener?.onClick(v)
            }
        }
        tv_pay_content.text = title
        tv_pay_need.text = "需支付:  ${moneyNeed}瑞点"
        tv_pay_have.text = "余额:  ${moneyHave}瑞点"
    }

    var okClickListener: View.OnClickListener? = null
}