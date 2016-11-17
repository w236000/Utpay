package com.utouu.utpay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;


/**
 * 支付宝支付
 */
public class AliPay {

    /**
     * @param payInfo  支付商品对象  请求APP服务端返回
     * @param mHandler 支付结果回调
     * @param payFlag  支付宝回调的msg.what
     * @param activity 调起支付Activity
     */
    public static void pay(final String payInfo, final Handler mHandler, final int payFlag, final Activity activity) {
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
}
