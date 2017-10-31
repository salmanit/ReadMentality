package com.xr.ruidementality.custom

import android.text.TextWatcher

/**
 * Created by Sage on 2017/9/27.
 * Description:
 */
abstract class  TextWatcherSimple:TextWatcher{
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}