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
}
