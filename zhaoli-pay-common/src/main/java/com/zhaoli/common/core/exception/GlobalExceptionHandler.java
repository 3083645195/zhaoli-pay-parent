package com.zhaoli.common.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局捕获异常
 */
@Slf4j
// 注解表明这是一个全局异常处理类,用于统一处理指定包下的控制器抛出的异常
@ControllerAdvice(basePackages = "com.zhaoli")
public class GlobalExceptionHandler {
    /**
     * 类中定义了一个用于处理RuntimeException及其子类异常的errorResult方法，
     * 使用@ExceptionHandler(RuntimeException.class)注解来指示处理的异常类型。
     * 当控制器中抛出RuntimeException异常时，Spring将会调用errorResult方法来处理该异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String, Object> errorResult(Exception e) {
        log.error("<error{}>", e);
        Map<String, Object> errorResultMap = new HashMap<String, Object>();
        errorResultMap.put("code", "500");
        errorResultMap.put("msg", "系统出现错误!");
        return errorResultMap;
    }
}