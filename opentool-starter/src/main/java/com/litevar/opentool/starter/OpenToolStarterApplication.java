package com.litevar.opentool.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.litevar.opentool.server", "com.litevar.opentool.starter"})
public class OpenToolStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenToolStarterApplication.class, args);
    }
}