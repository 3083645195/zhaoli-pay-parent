package com.zhaoli.pay.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */

/**
 * 利用AOP解决跨域的问题
 */
@Component
@Aspect//用于标识一个类为切面（Aspect），它通常与 Spring AOP（Aspect-Oriented Programming，面向切面编程）一起使用
public class ControllerCrossAspect {
    /**
     * 定义切点表达式
     * 对包下所有的controller结尾的类的所有方法增强
     */
    private final String executeExpr = "execution(* com.zhaoli.pay.controller.*.*(..)))";


    @After(executeExpr)//后置通知
    public void afterAdvice() {
        // 后置通知的逻辑
        //解决跨域问题
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        resp.setHeader("Access-Control-Allow-Origin", "*");
    }


}
