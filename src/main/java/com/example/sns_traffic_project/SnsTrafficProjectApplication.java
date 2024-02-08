package com.example.sns_traffic_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class SnsTrafficProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnsTrafficProjectApplication.class, args);
    }

}