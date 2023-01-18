package nl.brianvermeer.workshop.coffee;

import nl.brianvermeer.workshop.coffee.domain.Person;
import nl.brianvermeer.workshop.coffee.domain.Product;
import nl.brianvermeer.workshop.coffee.domain.ProductType;
import nl.brianvermeer.workshop.coffee.domain.Role;
import nl.brianvermeer.workshop.coffee.repository.PersonRepository;
import nl.brianvermeer.workshop.coffee.repository.ProductRepository;
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
    public CommandLineRunner initUsers(PersonRepository personRepo) {
        return (args) -> {
            personRepo.save(createUser("BrianVermeer", "Brian", "Vermeer", "brian@brianvermeer.nl", Role.ROLE_CUSTOMER, "$2a$10$pVh1nyJ3u7DaoR0AK6umlOnunQ8PFjix6xD7OayrQSvBGf72eZZOe"));
            personRepo.save(createUser("Admin", "Ad", "Min", "ad@min.com", Role.ROLE_ADMIN, "$2a$10$pVh1nyJ3u7DaoR0AK6umlOnunQ8PFjix6xD7OayrQSvBGf72eZZOe"));

            personRepo.findAll().forEach(System.out::println);
        };
    }

    @Bean
    public CommandLineRunner initProducts(ProductRepository productRepo) {
        return (args) -> {
            productRepo.save(new Product("Americano", "Black Coffee with hot water", 3.50, ProductType.COFFEE));
            productRepo.save(new Product("Espresso", "Small strong black coffee", 3.20, ProductType.COFFEE));
            productRepo.findAll().forEach(System.out::println);
        };
    }

    public Person createUser(String username, String firstName, String lastName, String email, Role role, String encPassw) {
        var user = new Person();
        user.setUsername(username);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setEmail(email);
        user.setRoles(role);
        user.setEncryptedPassword(encPassw);
        return user;
    }

}