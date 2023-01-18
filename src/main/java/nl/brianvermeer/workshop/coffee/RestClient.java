package nl.brianvermeer.workshop.coffee;

import nl.brianvermeer.workshop.coffee.domain.Order;
import nl.brianvermeer.workshop.coffee.domain.OrderLine;
import nl.brianvermeer.workshop.coffee.domain.Person;
import nl.brianvermeer.workshop.coffee.domain.Product;
import nl.brianvermeer.workshop.coffee.domain.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class RestClient {
    private static final String API_BASE_URL = "http://localhost:8080/api";
    private static final Logger log = LoggerFactory.getLogger(RestClient.class);
    private static final RestTemplate restTemplate = new RestTemplate();

    private static void listAllProducts() {
        log.info("-----List All Products-----");
        Product[] allProducts = restTemplate.getForObject(API_BASE_URL + "/products", Product[].class);
        if (allProducts != null && allProducts.length > 0) {
            for (Product product : allProducts) {
                log.info(product.toString());
            }
        } else {
            log.warn("No products found");
        }
    }

    private static Product getOneProduct(Long id) {
        log.info("-----Get One Product (ID = {})-----", id);
        Product product = restTemplate.getForObject(API_BASE_URL + "/products/" + id, Product.class);
        if (product == null) {
            log.info("Product with id {} not found", id);
        }
        return product;
    }

    private static Long createProduct(Product product) {
        log.info("-----Create Product-----");
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(API_BASE_URL + "/products", product, Product.class);
        log.info("Location: " + responseEntity.getHeaders().getLocation());
        return responseEntity.getBody().getId();
    }

    private static void updateProduct(Long id, Product product) {
        log.info("-----Update Product (ID = {})-----", id);
        restTemplate.put(API_BASE_URL + "/products/" + id, product);
    }

    private static void deleteProduct(Long id) {
        log.info("-----Delete Product (ID = {})-----", id);
        restTemplate.delete(API_BASE_URL + "/products/" + id);
    }

    private static void listAllPersons() {
        log.info("-----List All Persons-----");
        Person[] allPersons = restTemplate.getForObject(API_BASE_URL + "/persons", Person[].class);
        if (allPersons != null && allPersons.length > 0) {
            for (Person person : allPersons) {
                log.info(person.toString());
            }
        } else {
            log.warn("No persons found");
        }
    }

    private static Person getOnePerson(Long id) {
        log.info("-----Get One Person (ID = {})-----", id);
        Person person = restTemplate.getForObject(API_BASE_URL + "/persons/" + id, Person.class);
        if (person == null) {
            log.info("Person with id {} not found", id);
        }
        return person;
    }

    private static Long createPerson(Person person) {
        log.info("-----Create Person-----");
        ResponseEntity<Person> responseEntity = restTemplate.postForEntity(API_BASE_URL + "/persons", person, Person.class);
        log.info("Location: " + responseEntity.getHeaders().getLocation());
        return responseEntity.getBody().getId();
    }

    private static void updatePerson(Long id, Person person) {
        log.info("-----Update Person (ID = {})-----", id);
        restTemplate.put(API_BASE_URL + "/persons/" + id, person);
    }

    private static void deletePerson(Long id) {
        log.info("-----Delete Person (ID = {})-----", id);
        restTemplate.delete(API_BASE_URL + "/persons/" + id);
    }

    private static Long createOrder(Order order) {
        log.info("-----Create Order-----");
        ResponseEntity<Order> responseEntity = restTemplate.postForEntity(API_BASE_URL + "/orders", order, Order.class);
        log.info("Location: " + responseEntity.getHeaders().getLocation());
        return responseEntity.getBody().getId();
    }

    private static void listAllOrders() {
        log.info("-----List All Orders-----");
        Order[] allOrders = restTemplate.getForObject(API_BASE_URL + "/orders", Order[].class);
        if (allOrders != null && allOrders.length > 0) {
            for (Order order : allOrders) {
                log.info(order.toString());
            }
        } else {
            log.warn("No orders found");
        }
    }

    public static void main(String[] args) {
        listAllProducts();
        Long productId = createProduct(new Product("Product 1", "Description", 5.0, ProductType.COFFEE));
        Product product = getOneProduct(productId);
        product.setPrice(8.5);
        updateProduct(product.getId(), product);
        listAllProducts();

        listAllPersons();
        Long personId = createPerson(new Person("john.doe", "John", "Doe", "john.doe@mail.com", "123456789", "Fairfield, IA, US 52556"));
        Person person = getOnePerson(personId);
        person.setPhone("987654321");
        updatePerson(personId, person);
        listAllPersons();

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setPerson(person);

        OrderLine orderLine = new OrderLine();
        orderLine.setOrder(order);
        orderLine.setProduct(product);
        orderLine.setQuantity(10);

        createOrder(order);
        listAllOrders();
    }
}
