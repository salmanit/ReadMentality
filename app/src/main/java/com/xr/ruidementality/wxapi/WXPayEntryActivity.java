package com.xr.ruidementality.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xr.ruidementality.util.UtilLog;

/**
 * Created by xaozu on 15/10/27.
 * 微信支付结果
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    public static String WECHAT_APPID = "wx908f74fc610d8ae8";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WECHAT_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        System.out.println("wx=========onReq=" + req.getType());
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:

                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                ShowMessageFromWX.Req request = (ShowMessageFromWX.Req) req;
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        UtilLog.INSTANCE.i("wx 支付结果=========errCode=" + resp.errCode);
        String ms = "微信支付结果";
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            ms = "支付成功";

        } else if (resp.errCode == -1) {
            ms = "微信支付结果：程序错误，请联系客服！";
        } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            ms = "取消支付！";
//                cancelActivity();
        } else {
            ms = "未知错误！";
        }
        if (mWxPay != null)
            mWxPay.onWxPay(resp.errCode, ms);
        finish();
    }

    private static WxPayCallBack mWxPay;

    //微信支付回调
    public interface WxPayCallBack {
        void onWxPay(int code, String msg);
    }

    public static void setWxPay(WxPayCallBack wxPay) {
        mWxPay = wxPay;
    }
}
