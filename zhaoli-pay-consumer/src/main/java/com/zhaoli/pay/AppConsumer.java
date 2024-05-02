package com.zhaoli.pay;

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
public class AppConsumer {
    public static void main(String[] args) {
        SpringApplication.run(AppConsumer.class);
    }
}
