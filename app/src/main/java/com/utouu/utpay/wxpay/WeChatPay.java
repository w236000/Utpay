package com.utouu.utpay.wxpay;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import cz.msebera.android.httpclient.conn.util.InetAddressUtils;

/**
 * 微信支付
 */
public class WeChatPay {
    private static IWXAPI api;

    public static void pay(Context context, WxGoodInfo goodInfo) {
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
    /**
     * 得到本机IP地址，WIFI下获取的是局域网IP，数据流量下获取的是公网IP
     *
     * @return
     */
    public static String getIPAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements()) {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress()
                            && InetAddressUtils.isIPv4Address(ip
                            .getHostAddress())) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Log.e("WeChatPay", "获取本地ip地址失败", e);
        }
        return null;
    }

}
