package nl.brianvermeer.workshop.coffee.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
public class OrderLine {
    @Id
    @GeneratedValue
    private Long id;

    @Min(value = 1)
    private int quantity;

    @OneToOne
    private Product product;

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getSubtotal() {
        return quantity * product.getPrice();
    }

    public double getPrice() {
        return product.getPrice();
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product=" + product +
                ", order=" + order +
                '}';
    }
}
