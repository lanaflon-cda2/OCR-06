package com.paymybuddy.transferapps;

import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserAccountRepository.class)
public class TransferappsApplication {
    private static final Logger log;

    public static void main(String[] args) {
        SpringApplication.run(TransferappsApplication.class, args);
    }

    static {
        log = LogManager.getLogger((Class) TransferappsApplication.class);
    }
}
