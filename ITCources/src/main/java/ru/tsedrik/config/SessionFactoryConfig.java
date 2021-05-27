package ru.tsedrik.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionFactoryConfig {

    @Bean
    public SessionFactory sessionFactory(){
        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ru.tsedrik.entity.Student.class)
                .addAnnotatedClass(ru.tsedrik.entity.Course.class)
                .buildSessionFactory();
        return sessionFactory;
    }
}
