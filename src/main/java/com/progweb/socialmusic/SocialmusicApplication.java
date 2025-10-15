package com.progweb.socialmusic;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SocialmusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialmusicApplication.class, args);
    }

    @Component
    public static class Runner implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("Rodou corretamente!!");
        }
    }
}
