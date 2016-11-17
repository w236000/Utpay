package com.utouu.utpay.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.utouu.pay.alipay.AliPay;
import com.utouu.pay.httputil.GetPayInfo;
import com.utouu.pay.wxpay.WeChatPay;
import com.utouu.pay.wxpay.WxPayInfo;

import net.sourceforge.simcpux.R;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 支付宝支付回调的msg.what
     */
    private final int payFlag = 3;
    /**
     * 支付宝数据回调的msg.what
     */
    private final int aliInfoFlag = 1;
    /**
     * 微信数据回调的msg.what
     */
    private final int wxInfoFlag = 2;
    /**
     * 微信商品支付数据   请求APP服务端返回
     */
    private WxPayInfo goodInfo;
    /**
     * 支付宝商品支付数据  请求APP服务端返回
     */
    private String orderId;
    /**
     * 支付宝商品支付数据  请求APP服务端返回
     */
    private String handlerInfo;
    private static IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = WXAPIFactory.createWXAPI(this, "wx43ae11bc04ca7ad4", false);
//        findViewById(R.id.btn_alipay).setOnClickListener(this);
        findViewById(R.id.btn_wechatpay).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alipay:

//                GetPayInfo.getHttpInfo(null, 1, "", mHandler);

                break;
            case R.id.btn_wechatpay:
                GetPayInfo.getHttpInfo(null, 2, "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android", mHandler);
                break;
            default:
                break;
        }
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /**
                 * 支付宝支付数据回调
                 */
                case aliInfoFlag:
                    handlerInfo = (String) msg.obj;
                    if (!handlerInfo.equals("error")) {
                        AliPay.pay(orderId, mHandler, payFlag, MainActivity.this);
                    } else {
                        Toast.makeText(MainActivity.this, "网路连接失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                /**
                 * 微信支付数据回调
                 */
                case wxInfoFlag:
                    handlerInfo = (String) msg.obj;
                    if (null != handlerInfo) {
                        WxPayInfo req = new WxPayInfo();
                        req.appId = "wxb4ba3c02aa476ea1";
                        req.partnerId = "1305176001";
                        req.prepayId = "wx201611161024501964255d730351243594";
                        req.nonceStr = "6dd46e72657173f8d0f634538114bad5";
                        req.timeStamp = "1479263090";
                        req.packageValue = "Sign=WXPay";
                        req.sign = "892F121F0C8FCC7D5AD4E19A0BD27A64";
                        goodInfo=req;
                    } else {
                        Log.d("PAY_GET", "返回错误" );
                        Toast.makeText(MainActivity.this, "返回错误" , Toast.LENGTH_SHORT).show();
                    }
                    if (!handlerInfo.equals("error")) {
                        WeChatPay.pay(MainActivity.this, goodInfo);
                    } else {
                        Toast.makeText(MainActivity.this, "网路连接失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                /**
                 * 支付宝支付成功回调
                 */
                case payFlag:
                    handlerInfo = (String) msg.obj;
                    Toast.makeText(MainActivity.this, "支付宝回调" + handlerInfo, Toast.LENGTH_SHORT).show();

                    break;
            }
        }

        ;
    };

}
