package com.ccnu.bbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BbsApplication.class, args);
    }

}
