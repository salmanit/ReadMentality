package com.xr.ruidementality.custom

import com.xr.ruidementality.bean.BaseEntity
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sage on 2017/9/28.
 * Description:因为使用了自定义的回调，所以大家处理起来都一样了，
 */
object NormalHandle{

    fun <T> handleObserver( observable: Observable<BaseEntity<T>>,customCallBack: CustomCallBack<T>?){
        observable.compose(RxHelper<T>().handleBase())
                .subscribe(object :RxSubscribe<T>(){
                    override fun onNext(t: T) {
                        customCallBack?.onSuccess(t)
                    }

                    override fun _onError(type: Int, msg: String) {
                        customCallBack?.onError(type,msg)
                    }

                    override fun onComplete() {
                        customCallBack?.onComplete()
                    }
                })
    }

    fun <T> handleResult():ObservableTransformer<BaseEntity<T>,T>{

      return ObservableTransformer { upstream ->
          upstream.flatMap(Function<BaseEntity<T>, ObservableSource<T>> { t ->
              if (t.info != 1) {
                  return@Function Observable.error(RxException(t.tip))
              }
              Observable.create<T> { e ->
                  try {
                      if(t.data!=null)
                          e.onNext(t.data!!)
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
    var num=0
    fun test(){
        var ob1=Observable.create<String> {
            if(num%2==0){
                it.onNext("num=$num")
            }else{
                it.onComplete()
            }
            num++
            }
        var ob2=Observable.create<String> { it.onNext("222222") }
        Observable.concat(ob1,ob2).firstElement().subscribe {
            println("aaaa===========$it")
        }
    }
}