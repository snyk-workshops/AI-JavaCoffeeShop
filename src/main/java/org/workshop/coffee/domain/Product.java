package org.workshop.coffee.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String productName;

    private String description;

    @NotNull(message = "Product price cannot be empty")
    @Min(value = 0, message = "Product price must be greater than or equal to 0")
    private Double price;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Product type cannot be empty")
    private ProductType productType;

    public Product() {
        super(); // default constructor
    }

    public Product(String productName, String description, double price, ProductType productType) {
        super();
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.productType = productType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", productType=" + productType +
                '}';
    }
}
