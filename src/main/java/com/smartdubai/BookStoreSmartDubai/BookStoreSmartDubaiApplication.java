package com.smartdubai.BookStoreSmartDubai;

import com.smartdubai.config.BookConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.smartdubai.entity"})
@ComponentScan(basePackages = {"com.smartdubai.*"})
@EnableJpaRepositories(basePackages = {"com.smartdubai.*"})
@Import(BookConfiguration.class)
public class BookStoreSmartDubaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreSmartDubaiApplication.class, args);
	}

}
