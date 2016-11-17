package com.utouu.utpay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.utouu.utpay.wxpay.WxGoodInfo;

/**
 * Created by You on 2016/11/16.
 */

public class UtPayUtil {

    /**
     * @param payInfo  支付商品对象  请求APP服务端返回
     * @param mHandler 支付结果回调
     * @param payFlag  支付宝回调的msg.what
     * @param activity 调起支付Activity
     */
    public static void AliPay(final String payInfo, final Handler mHandler, final int payFlag, final Activity activity) {
        new Thread() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity); // 构造PayTask 对象
                String result = alipay.pay(payInfo, true); // 调用支付接口，获取支付结果
                Message msg = new Message();
                msg.what = payFlag;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }.start();
    }
    private static IWXAPI api;

    public static void WxPay(Context context, WxGoodInfo goodInfo) {
        api = WXAPIFactory.createWXAPI(context, goodInfo.appId,
                false);
        // 检测是否安装了微信
        boolean isWeChat = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isWeChat) {
            sendPayReq(goodInfo);
        } else {
            Toast.makeText(context, "未安装微信", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 发送支付请求
     *
     * @param goodInfo 从服务端获取到的商品支付信息
     */
    private static void sendPayReq(WxGoodInfo goodInfo) {
        PayReq req = new PayReq();
        req.appId = goodInfo.appId;
        req.partnerId = goodInfo.partnerId;
        req.prepayId = goodInfo.prepayId;
        req.nonceStr = goodInfo.nonceStr;
        req.timeStamp = goodInfo.timeStamp;
        req.packageValue = goodInfo.packageValue;
        req.sign = goodInfo.sign;
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.registerApp(goodInfo.appId);
        api.sendReq(req);
    }
}
