

package io.information;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"io.information","io.elasticsearch","io.mq"})
@EnableTransactionManagement
@EnableCaching
public class BetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetApplication.class, args);
	}
}