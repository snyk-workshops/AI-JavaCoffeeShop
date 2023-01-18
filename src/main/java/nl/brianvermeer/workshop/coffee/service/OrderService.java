package nl.brianvermeer.workshop.coffee.service;

import nl.brianvermeer.workshop.coffee.domain.Order;
import nl.brianvermeer.workshop.coffee.domain.Person;
import nl.brianvermeer.workshop.coffee.domain.Product;
import nl.brianvermeer.workshop.coffee.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public List<Order> findByProduct(Product product) {
        return orderRepository.findDistinctOrderByOrderLines_Product(product);
    }

    public List<Order> findByPerson(Person person) {
        return orderRepository.findOrderByPerson(person);
    }

    public List<Order> findByDate(Date minDate, Date maxDate) {
        return orderRepository.findOrderByOrderDateBetween(minDate, maxDate);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

}
