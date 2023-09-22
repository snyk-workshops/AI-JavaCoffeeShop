package org.workshop.coffee.export;

import org.workshop.coffee.domain.OrderLine;

public class ExportOrderLine {
    private Long id;

    private int quantity;

    private String productName;

    private Double price;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ExportOrderLine{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }

    public static ExportOrderLine fromOrderLine(OrderLine orderLine) {
        var exportOrderLine = new ExportOrderLine();
        exportOrderLine.setId(orderLine.getId());
        exportOrderLine.setPrice(orderLine.getPrice());
        exportOrderLine.setQuantity(orderLine.getQuantity());
        exportOrderLine.setProductName(orderLine.getProductName());
        return exportOrderLine;
    }
}
