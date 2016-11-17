package com.utouu.utpay.httputil;

import android.os.Handler;
import android.os.Message;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;


/**
 * 获取支付参数
 */
public class GetPayInfo {

    /**
     * 请求服务端获得支付信息
     *
     * @param params  需要给服务端的参数
     * @param flag    支付宝微信的标示
     * @param url     请求服务端的地址
     * @param handler 获取支付信息后的回调
     */

    public static void getHttpInfo(Map<String, String> params, final int flag, String url, final Handler handler) {
        final Message msg = new Message();
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        msg.obj = "error";
                        msg.what = flag;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(String response) {
                        msg.obj = response;
                        msg.what = flag;
                        handler.sendMessage(msg);
                    }
                });
    }
}

