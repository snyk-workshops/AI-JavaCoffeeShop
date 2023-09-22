package org.workshop.coffee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CoffeeShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner fill(Filler filler) {
        System.out.println("start filling");
        return (args) -> {
            filler.createAdmin("Admin", "admin");
            filler.createCustomer("simon", "123123");
            filler.createPeople(6);
            filler.createCoffees(10);
            filler.createBeers(8);
            filler.createOrders(3);
            System.out.println("READY!");
        };
    }
}