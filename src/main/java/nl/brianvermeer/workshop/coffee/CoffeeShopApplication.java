package nl.brianvermeer.workshop.coffee;

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
            filler.createAdmin("Admin", "qqq");
            filler.createCustomer("Brian", "qwerty");
            filler.createPeople(10);
            filler.createCoffees(10);
            filler.createBeers(15);
            filler.createOrders();
            System.out.println("READY!");
        };
    }


}