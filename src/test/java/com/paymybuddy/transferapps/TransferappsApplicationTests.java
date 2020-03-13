package com.paymybuddy.transferapps;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(classes = {TransferappsApplication.class})
@TestPropertySource(locations="classpath:application-integrationtest.properties")
@SpringBootTest
public class TransferappsApplicationTests {
    public static void main(final String[] args) {
        SpringApplication.run(TransferappsApplication.class, args);
    }
}
