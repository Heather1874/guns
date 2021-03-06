package com.stylefeng.guns.rest;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
@EnableTransactionManagement
@EnableDubboConfiguration
public class GunsSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(GunsSeckillApplication.class, args);
    }
}
