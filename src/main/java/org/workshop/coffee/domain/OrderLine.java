package org.workshop.coffee.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class OrderLine {
    @Id
    @GeneratedValue
    private Long id;

    @Min(value = 1)
    private int quantity;

    @Transient
    private Product product;

    @Column(length = 10000)
    private String productName;

    @NotNull(message = "Product price cannot be empty")
    @Min(value = 0, message = "Product price must be greater than or equal to 0")
    private Double price;

    @ManyToOne
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getSubtotal() {
        return quantity * price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product='" + product + '\'' +
                ", price=" + price +
                ", order=" + order +
                '}';
    }
}
