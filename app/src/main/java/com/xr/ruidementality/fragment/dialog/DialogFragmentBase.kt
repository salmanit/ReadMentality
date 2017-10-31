package com.xr.ruidementality.fragment.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.xr.ruidementality.R

/**
 * Created by Sage on 2017/10/19.
 * Description:
 */
open abstract class DialogFragmentBase() : DialogFragment() {
    abstract fun getLayout(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val window = dialog.window
        window!!.setBackgroundDrawable(ColorDrawable())
        val wl = window.attributes
        wl.width = WindowManager.LayoutParams.MATCH_PARENT
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT
        wl.dimAmount = 0.5f
        wl.flags = wl.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        resetParams(wl)
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl)
        // 设置点击外围取消
        dialog.setCanceledOnTouchOutside(cancelOutside)
        dialog.setCancelable(cancelBack)
    }

    var cancelOutside = false
    var cancelBack = false
    open fun resetParams(params: WindowManager.LayoutParams) {

    }

    init {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DiaScaleAnimationTheme)
    }
}