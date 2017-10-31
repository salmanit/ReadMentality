package com.xr.ruidementality.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import org.simple.eventbus.EventBus

open class ActivityBase : AppCompatActivity() {

    open fun eventBusEnable(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (eventBusEnable()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (eventBusEnable()) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun dispatchGenericMotionEvent(ev: MotionEvent?): Boolean {
        return super.dispatchGenericMotionEvent(ev)
    }
}
