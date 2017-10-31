package com.xr.ruidementality.fragment.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import com.xr.ruidementality.R
import com.xr.ruidementality.fragment.FragmentBase
import com.xr.ruidementality.util.UtilNormal
import kotlinx.android.synthetic.main.fragment_search.*
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber

/**
 * Created by Sage on 2017/10/17.
 * Description:输入关键字搜索音视频
 */
class FragmentSearch : FragmentBase() {
    override fun getLayout(): Int {
        return R.layout.fragment_search
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null)
            replaceFragment(FragmentSearchTag())
        tv_cancel.setOnClickListener {
            onBackPress()
        }
        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var key = et_search.text.toString()
                if (TextUtils.isEmpty(key)) {
                    showToast("请输入关键字")
                } else {
                    search(key)
                }
                return@setOnEditorActionListener true
            } else {
                return@setOnEditorActionListener false
            }
        }
    }

    override fun eventBusEnable(): Boolean {
        return true
    }

    @Subscriber(tag = "searchKeyWord")
    fun searchFromHotWord(key: String) {
        et_search.setText(key)
        search(key)
    }

    fun search(key: String) {
        UtilNormal.hideSoftInput(activity)
        var fragment = childFragmentManager.findFragmentByTag(FragmentSearchResult::class.java.simpleName)
        if (fragment != null) {
            var fragmentResult = fragment as FragmentSearchResult
            fragmentResult.key2 = key
            EventBus.getDefault().post(key, "refreshSearchByKeY")
        } else {
            replaceFragment(FragmentSearchResult().apply { key2 = key })
        }
    }

    fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.push_left_in, R.anim.push_left_in, R.anim.back_left_in, R.anim.back_right_out)
                .replace(R.id.layout_content, fragment,
                        fragment::class.java.simpleName)
                .commitAllowingStateLoss()
    }
}