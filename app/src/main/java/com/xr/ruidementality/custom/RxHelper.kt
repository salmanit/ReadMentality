package com.xr.ruidementality.custom

import com.xr.ruidementality.bean.BaseEntity
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sage on 2017/9/27.
 * Description:将基类里我们需要的实体类取出来返回
 */
class RxHelper<T>{

    fun handleBase(): ObservableTransformer<BaseEntity<T>, T> {
        return ObservableTransformer { upstream ->
            upstream.flatMap<T>(Function<BaseEntity<T>, ObservableSource<T>> { tBaseReply ->
                if (tBaseReply.info != 1) {
                    return@Function Observable.error(RxException(tBaseReply.tip))
                }
                Observable.create<T> { e ->
                    try {
                        if(tBaseReply.data!=null)
                            e.onNext(tBaseReply.data!!)
                        e.onComplete()
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        e.onError(ex)
                    }

                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}