package com.xr.ruidementality.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.xr.ruidementality.R
import com.xr.ruidementality.ui.ActivityHome
import com.xr.ruidementality.util.SpHelper
import com.xr.ruidementality.util.StatusBarUtil
import kotlinx.android.synthetic.main.layout_toolbar_normal.*
import org.simple.eventbus.EventBus

/**
 * Created by Sage on 2017/9/26.
 * Description:
 */
open abstract class FragmentBase : Fragment() {

    abstract fun getLayout(): Int

    open fun eventBusEnable(): Boolean {
        return false
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayout(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (eventBusEnable()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (eventBusEnable()) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun addFragment(fragment: Fragment) {
        (activity as ActivityHome).addFragment(fragment)
    }

    open fun onBackPress() {
        activity.supportFragmentManager.popBackStack()
    }

    open fun isLogin(): Boolean {
        return !TextUtils.isEmpty(SpHelper.getUserInfo().id)
    }

    open fun normalToolbar(title: String) {
        iv_back.setOnClickListener { onBackPress() }
        tv_title.text = title
    }

    var toast: Toast? = null
    fun showToast(msg: String) {
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(activity, msg + "", Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun getPlaceHolderFooter(view: ViewGroup): View {
        return LayoutInflater.from(activity).inflate(R.layout.footer_place_holder, view, false)
    }

    fun addPaddingMatchStateBar(vararg views: View) {
        views.forEach { StatusBarUtil.setPaddingSmart(activity, it) }
    }
}