package com.paymybuddy.paymybuddyserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayMyBuddyServerApplication implements CommandLineRunner {

    /**
     * Main method.
     * @param args String[]
     */
    public static void main(final String[] args) {
        SpringApplication.run(PayMyBuddyServerApplication.class, args);
    }

    @Override
    public void run(final String... args) {
    }
}
