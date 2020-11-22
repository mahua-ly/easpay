package com.kalo.easpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"com.kalo"})
@MapperScan(basePackages = "com.kalo.easpay.domain.mapper")
public class EaspayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EaspayApplication.class, args);
    }

}
