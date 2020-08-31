package com.drips.disk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lijb
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class NetDiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetDiskApplication.class, args);
    }
}
