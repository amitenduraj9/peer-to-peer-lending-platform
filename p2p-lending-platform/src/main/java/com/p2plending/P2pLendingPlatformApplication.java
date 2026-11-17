package com.p2plending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Entry point of the P2P Lending Platform.
 * Extends SpringBootServletInitializer so the app can ALSO be deployed
 * as a traditional WAR file on an external Tomcat server if needed,
 * while still being runnable directly via `mvn spring-boot:run`.
 */
@SpringBootApplication
public class P2pLendingPlatformApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(P2pLendingPlatformApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(P2pLendingPlatformApplication.class);
    }
}
