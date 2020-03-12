package com.paymybuddy.transferapps;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TransferappsApplication.class})
public class TransferappsApplicationTests {
	public static void main(final String[] args) {
		SpringApplication.run(TransferappsApplication.class, args);
	}
}
