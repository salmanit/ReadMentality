package com.xr.ruidementality.custom

import com.xr.ruidementality.util.UtilLog
import com.xr.ruidementality.util.UtilNormal
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Sage on 2017/10/26.
 * Description:
 */
class ReadCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var netHave = UtilNormal.isNetworkConnected()
        var request = chain.request();
        request = request.newBuilder()
                .cacheControl(if (netHave) CacheControl.FORCE_NETWORK else CacheControl.FORCE_CACHE)
                .build();
        UtilLog.i("============have network?===$netHave")
        var originalResponse = chain.proceed(request);
        if (netHave) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    // 有网络时 设置缓存超时时间1个小时
//                    .header("Cache-Control", "public, max-age=" + 60 * 60)
                    .build();

//            var cacheControl = request.cacheControl().toString();
//            return originalResponse.newBuilder()
//                    .header("Cache-Control", cacheControl)
//                    .removeHeader("Pragma")
//                    .build();
        } else {

            return  originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    // 无网络时，设置超时为1周
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 7 * 24 * 60 * 60)
                    .build();
//            return originalResponse.newBuilder()
//                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
//                    .removeHeader("Pragma")
//                    .build();
        }


    }
}