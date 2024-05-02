package com.zhaoli.pay.utils;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */

import java.util.UUID;

/**
 * 获取随机token
 */
public class TokenUtils {
    public static String getToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
