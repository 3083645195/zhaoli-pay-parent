package com.zhaoli.pay.constant;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
public interface Constant {
    /**
     * 青云客返回结果
     */
    Integer QINGYUNKE_RESULT_OK = 0;
    /**
     * 表示一个对象为空
     */
    Object VALUE_IS_NULL = null;
    /**
     * 表示一个整数的默认值为零
     */
    Integer DEFAULT_VALUE_ZERO = 0;
    /**
     * redis 存入msgid记录重试次数时的前缀
     */
    String REDIS_PREFIX_MSGID = "msgid:";
    /**
     * redis 存入qrCodeToken对应的openId的前缀
     */
    String REDIS_PREFIX_QRCODETOKEN = "qrCodeToken:";
    /**
     * 未审核状态
     */
    Integer MERCHANT_UNAPPROVED_STATUS = 0;
    /**
     * 审核通过
     */
    Integer MERCHANT_SUCCESS_STATUS = 1;
    /**
     * 审核失败
     */
    Integer MERCHANT_FAIL_STATUS = 2;
    /**
     * 生成二维码有效期 30天
     */
    Integer QRCODE_EXPIRE_SECONDS = 2592000;
    /**
     * 数据库增删改操作的返回结果大于零
     */
    Integer DB_RESULT_BIGZERO = 0;
    /**
     * 返回空字符串
     */
    String RESULT_STRING_NULL = null;
    /**
     * redis 存入 payToken 支付令牌前缀
     */
    String REDIS_PREFIX_PAYTOKEN = "payToken:";
    /**
     * 订单支付结果 1：表示已支付
     */
    Integer PAY_PAYMENT_COMPLETED=1;

    String ALI_PAY_TRADESTATUS_SUCCESS = "TRADE_SUCCESS";

    String ALI_PAY_QUERY_RESPONSE_CODE = "10000";
}
