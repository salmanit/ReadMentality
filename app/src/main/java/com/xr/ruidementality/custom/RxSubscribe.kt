package com.xr.ruidementality.custom

import com.xr.ruidementality.app.MyApplication
import com.xr.ruidementality.util.UtilInternet
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by Sage on 2017/9/27.
 * Description:封装错误信息，对错误信息进行处理
 */
abstract class RxSubscribe<T>:Observer<T>{
    override fun onError(e: Throwable) {
        if(e is RxException){
            _onError(0,e.message?:"")
        }else {
            //判断有无网络，没有
           if(UtilInternet.isNetworkConnected(MyApplication.myApp)){
               _onError(2,"连接服务器异常")
           }else{
               _onError(1,"网络异常")
           }
        }
        println("onError===============${e.message}")
        onComplete()
    }

    override fun onComplete() {

    }


    override fun onSubscribe(d: Disposable) {

    }

    abstract fun _onError( type:Int,msg: String)
}