package ru.tsedrik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = "ru.tsedrik.mongodb")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
