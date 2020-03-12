package com.paymybuddy.transferapps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TransferappsApplication {
    private static final Logger log;

    public static void main(String[] args) {
        SpringApplication.run(TransferappsApplication.class, args);
    }
/*
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
*/
    static {
        log = LogManager.getLogger((Class) TransferappsApplication.class);
    }
}
