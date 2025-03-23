package com.marciocesar.walletserviceassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.marciocesar.walletserviceassignment.api.configs")
@ComponentScan(basePackages = {
        "com.marciocesar.walletserviceassignment.core.services",
        "com.marciocesar.walletserviceassignment.api.controllers",
        "com.marciocesar.walletserviceassignment.core.mapper",
        "com.marciocesar.walletserviceassignment.api.mapper",
})
@EntityScan(basePackages = "com.marciocesar.walletserviceassignment.core.database.entities")
public class WalletServiceAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletServiceAssignmentApplication.class, args);
    }

}
