

package io.information;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetApplication.class, args);
	}
}