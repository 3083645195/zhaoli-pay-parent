package com.zhaoli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@SpringBootApplication
@MapperScan("com.zhaoli.pay.mapper")
public class AppPay {
    /**
     * @SpringBootApplication 扫包只会扫同级包 所以要将启动类放到 com.zhaoli 包下 这样就可以扫描到项目中所有的包
     * 不能放到 com.zhaoli.pay 中 因为工具类中的名称是以 com.zhaoli 为开头的
     */
    public static void main(String[] args) {
        SpringApplication.run(AppPay.class);
    }
}
