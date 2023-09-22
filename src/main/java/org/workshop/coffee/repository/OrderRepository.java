package org.workshop.coffee.repository;

import org.workshop.coffee.domain.Order;
import org.workshop.coffee.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByPerson(Person person);

    List<Order> findOrderByOrderDateBetween(Date minDate, Date maxDate);


}
