package com.lg.qapl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lg.qapl.mapper")
public class QaplApplication {

    public static void main(String[] args) {
        SpringApplication.run(QaplApplication.class, args);
    }

}
