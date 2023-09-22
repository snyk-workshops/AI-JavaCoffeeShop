package org.workshop.coffee.repository;

import org.workshop.coffee.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface ProductRepository extends JpaRepository<Product, Serializable> {

    public Product findProductByProductName(String productName);

}
