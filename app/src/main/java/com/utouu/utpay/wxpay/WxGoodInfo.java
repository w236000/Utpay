package com.utouu.utpay.wxpay;

/**
 * 微信调用所需参数
 */

public class WxGoodInfo {
    /**
     * 微信开放平台审核通过的应用APPID
     */
    public String appId;
    /**
     * 微信支付分配的商户号
     */
    public String partnerId;
    /**
     * 微信返回的支付交易会话ID
     */
    public String prepayId;
    /**
     * 随机字符串
     */
    public String nonceStr;
    /**
     * 时间戳
     */
    public String timeStamp;
    /**
     * 扩展字段暂填写固定值Sign=WXPay
     */
    public String packageValue;
    /**
     * 签名
     */
    public String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
