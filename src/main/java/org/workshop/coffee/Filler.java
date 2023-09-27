package org.workshop.coffee;

import net.datafaker.Faker;
import org.workshop.coffee.domain.Order;
import org.workshop.coffee.domain.OrderLine;
import org.workshop.coffee.domain.Person;
import org.workshop.coffee.domain.Product;
import org.workshop.coffee.domain.ProductType;
import org.workshop.coffee.domain.Role;
import org.workshop.coffee.service.OrderService;
import org.workshop.coffee.service.PersonService;
import org.workshop.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
public class Filler {

    private Faker faker = new Faker();

    @Autowired
    private PersonService personService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;



    public void createAdmin(String username, String pwd) {
        var newPerson = new Person(
                username,
                "ADMIN",
                "ADMIN",
                username + "@admin.com",
                null,
                null);
        newPerson.setPassword(pwd);
        newPerson.setRoles(Role.ROLE_ADMIN);
        personService.savePerson(newPerson);
    }

    public void createCustomer(String username, String pwd) {
        var newPerson = new Person(
                username,
                username,
                "Customer",
                username + "@customer.com",
                null,
                null);
        newPerson.setPassword(pwd);
        newPerson.setRoles(Role.ROLE_CUSTOMER);
        personService.savePerson(newPerson);
    }



    public void createPerson() {
        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();
        var username = (firstName + "." + lastName).toLowerCase(Locale.ROOT);
        var newPerson = new Person(
                username,
                firstName,
                lastName,
                username + "@gmail.com",
                faker.phoneNumber().phoneNumber(),
                faker.address().fullAddress());
        newPerson.setPassword(firstName);
        personService.savePerson(newPerson);
    }
    public void createPeople(int amount) {
        for (int i = 0; i < amount; i++) {
            createPerson();
        }
    }

    public void createCoffee() {
        var coffee = faker.coffee();
        var product = new Product(coffee.name1() + " " + coffee.name2(),
                "Body: " + coffee.body() + " \nTaste:  " + coffee.notes(),
                faker.number().randomDouble(2, 1, 4),
                ProductType.COFFEE
        );
        storeProduct(product);
        System.out.println("Created coffee: http://localhost:8081/products/direct?param=" + product.getProductName().replace(" ", "%20").replace("'","%27"));
    }

    public void createCoffees(int amount) {
        for (int i = 0; i < amount; i++) {
            createCoffee();
        }
    }

    public void createBeer() {
        var beer = faker.beer();
        var product = new Product(beer.name(),
                beer.style(),
                faker.number().randomDouble(4,1,12),
                ProductType.BEER
        );
        storeProduct(product);
    }

    public void createBeers(int amount) {
        for (int i = 0; i < amount; i++) {
            createBeer();
        }
    }

    private void storeProduct(Product prod) {
        try {
            productService.save(prod);
        } catch (Exception cve) {
            System.out.println("Skip product creation ["+prod.getProductName()+"] (possible duplicate name)");
        }
    }

    public void createOrders() {
        createOrders(5);
    }

    public void createOrders(int maxAmount) {
        var people = personService.getAllPersons();
        var products = productService.getAllProducts();
        people.forEach(p -> createOrdersforPerson(p, products, faker.number().numberBetween(1,maxAmount)));
    }

    private void createOrdersforPerson(Person p, List<Product> products, int amount) {
        for (int i = 0; i < amount; i++) {
            var newOrder = new Order();
            var ts = faker.date().past(100, TimeUnit.DAYS);

            newOrder.setOrderDate(new Date(ts.getTime()));
            newOrder.setPerson(p);
            var amountOfLines = faker.number().numberBetween(1,5);
            for (int j = 0; j < amountOfLines; j++) {
                newOrder.addOrderLine(createOrderLines(newOrder, products));
            }
            orderService.save(newOrder);
        }
    }

    private OrderLine createOrderLines(Order order, List<Product> products) {
            var line = new OrderLine();
            var product = products.get(faker.number().numberBetween(0,products.size()-1));
            line.setPrice(product.getPrice());
            line.setProductName(product.getProductName());
            line.setQuantity(faker.number().numberBetween(1,4));
            line.setOrder(order);
            return line;
    }


}
