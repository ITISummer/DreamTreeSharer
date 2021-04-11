package com.ITIS.DreamTreeSharer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName DreamTreeSharerApp
 * @Author LCX
 * @Date 2021 2021-03-26 2:15 p.m.
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan("com.ITIS.DreamTreeSharer.dao")
public class DreamTreeSharerApp {
    public static void main(String[] args) {
        SpringApplication.run(DreamTreeSharerApp.class,args);
    }
}
