package com.snow;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ASUS
 */
@MapperScan("com.snow.easyexcel.mapper") //扫描的mapper
@SpringBootApplication
public class SpringbootEasyexcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEasyexcelApplication.class, args);
    }

}
