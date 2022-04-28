package com.snow.eureka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author ASUS
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringBootEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEurekaApplication.class, args);
    }

}
