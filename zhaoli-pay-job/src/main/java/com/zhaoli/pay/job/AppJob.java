package com.zhaoli.pay.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 余胜军
 * @ClassName AppJob
 */
@SpringBootApplication
@MapperScan("com.mayikt.pay.job.mapper")
public class AppJob {
    public static void main(String[] args) {
        SpringApplication.run(AppJob.class);
    }
}
