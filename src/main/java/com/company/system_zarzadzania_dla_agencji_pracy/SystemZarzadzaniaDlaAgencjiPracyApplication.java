package com.company.system_zarzadzania_dla_agencji_pracy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SystemZarzadzaniaDlaAgencjiPracyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemZarzadzaniaDlaAgencjiPracyApplication.class, args);
    }

    @Bean
    public PasswordEncoder getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
