package com.zhaoli.pay.holder;

import java.util.Map;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */

/**
 * 使用ThreadLocal来存储每个线程的参数Map。add方法用于向当前线程设置参数，get方法用于获取当前线程的参数。
 *
 * 请注意，ThreadLocal是一个线程局部变量，它可以保证在每个线程中的数据相互隔离，不会被其他线程访问到。
 * 这种方式可以确保参数的安全性，在多线程环境下不会出现数据混乱的情况
 */
public class PayThreadInfoHolder {
    private static ThreadLocal<Map<String, String>> paramsThreadLocal = new ThreadLocal<>();

    public static void add(Map<String, String> params) {
        paramsThreadLocal.set(params);
    }
    public static Map<String, String> get(){
        return paramsThreadLocal.get();
    }
}
